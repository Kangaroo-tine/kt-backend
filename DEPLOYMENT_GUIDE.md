# KangarooTine MSA 배포 가이드

## 📋 개요
이 가이드는 KangarooTine MSA 백엔드 시스템을 네이버 클라우드 플랫폼의 Kubernetes 환경에 배포하는 방법을 설명합니다.

## 🏗️ 아키텍처 구성

### 서비스 구성
- **API Gateway** (포트: 8080): 외부 요청의 진입점
- **Member Service** (포트: 8081): 사용자 인증 및 관리
- **Planner Service** (포트: 8082): 일정 관리
- **Engagement Service** (포트: 8083): 참여 및 소통

### 데이터베이스 구성
- **MySQL 8.0**: 단일 서버, 논리적 스키마 분리
  - `member_db`: 사용자 관련 데이터
  - `planner_db`: 일정 관련 데이터
  - `engagement_db`: 참여 및 소통 관련 데이터

## 🚀 배포 단계

### 1단계: 네이버 클라우드 플랫폼 설정

#### 1.1 SourceCommit 설정
```bash
# SourceCommit 저장소 연결
git remote add ncp https://devtools.ncloud.com/3067145/kangarootine.git

# 코드 푸시
git add .
git commit -m "Initial MSA structure"
git push ncp master
```

#### 1.2 SourceBuild 설정
1. **네이버 클라우드 콘솔** → **SourceBuild** → **빌드 프로젝트 생성**
2. **소스 연결**: SourceCommit 저장소 선택
3. **빌드 환경**: Java 17, Gradle 선택
4. **빌드 명령어**:
   ```bash
   # Member Service 빌드
   cd member-service
   ./gradlew clean build -x test
   docker build -t kangaroo-tine/member-service:latest .
   
   # Planner Service 빌드
   cd ../planner-service
   ./gradlew clean build -x test
   docker build -t kangaroo-tine/planner-service:latest .
   
   # Engagement Service 빌드
   cd ../engagement-service
   ./gradlew clean build -x test
   docker build -t kangaroo-tine/engagement-service:latest .
   
   # API Gateway 빌드
   cd ../api-gw
   ./gradlew clean build -x test
   docker build -t kangaroo-tine/api-gateway:latest .
   ```

#### 1.3 Container Registry 설정
1. **네이버 클라우드 콘솔** → **Container Registry** → **이미지 저장소 생성**
2. **이미지 푸시**:
   ```bash
   # 각 서비스 이미지를 Container Registry에 푸시
   docker tag kangaroo-tine/member-service:latest ncr.ncloud.com/{프로젝트명}/member-service:latest
   docker push ncr.ncloud.com/{프로젝트명}/member-service:latest
   ```

### 2단계: Kubernetes 클러스터 설정

#### 2.1 네임스페이스 생성
```bash
kubectl apply -f k8s-manifests/namespace.yaml
```

#### 2.2 시크릿 생성
```bash
kubectl apply -f k8s-manifests/secrets.yaml
```

#### 2.3 ConfigMap 생성
```bash
kubectl apply -f k8s-manifests/configmap.yaml
```

#### 2.4 PVC 생성
```bash
kubectl apply -f k8s-manifests/pvc.yaml
```

### 3단계: 데이터베이스 배포

#### 3.1 MySQL 초기화 스크립트 생성
```bash
kubectl apply -f k8s-manifests/mysql-init-script.yaml
```

#### 3.2 MySQL 배포
```bash
kubectl apply -f k8s-manifests/mysql.yaml
```

#### 3.3 MySQL 상태 확인
```bash
kubectl get pods -n kangaroo-tine
kubectl logs -f deployment/mysql -n kangaroo-tine
```

### 4단계: 마이크로서비스 배포

#### 4.1 Member Service 배포
```bash
kubectl apply -f k8s-manifests/member-service.yaml
```

#### 4.2 Planner Service 배포
```bash
kubectl apply -f k8s-manifests/planner-service.yaml
```

#### 4.3 Engagement Service 배포
```bash
kubectl apply -f k8s-manifests/engagement-service.yaml
```

#### 4.4 API Gateway 배포
```bash
kubectl apply -f k8s-manifests/api-gateway.yaml
```

### 5단계: 배포 확인

#### 5.1 전체 서비스 상태 확인
```bash
kubectl get all -n kangaroo-tine
```

#### 5.2 로그 확인
```bash
# 각 서비스별 로그 확인
kubectl logs -f deployment/member-service -n kangaroo-tine
kubectl logs -f deployment/planner-service -n kangaroo-tine
kubectl logs -f deployment/engagement-service -n kangaroo-tine
kubectl logs -f deployment/api-gateway -n kangaroo-tine
```

#### 5.3 외부 접근 확인
```bash
# API Gateway 외부 IP 확인
kubectl get service api-gateway -n kangaroo-tine

# 헬스체크
curl http://{EXTERNAL-IP}:8080/actuator/health
```

## 🔧 환경변수 설정

### 필수 시크릿 값들
- `DB_USERNAME`: kangarootine
- `DB_PASSWORD`: kangarootineserver
- `JWT_SECRET`: kangaroo-tine-production-jwt-secret-key-2025
- `KAKAO_REST_API_KEY`: 79e73455589c19b1fc2f291bdeb4f9e8

### Base64 인코딩 방법
```bash
echo -n "kangarootine" | base64
echo -n "kangarootineserver" | base64
echo -n "kangaroo-tine-production-jwt-secret-key-2025" | base64
echo -n "79e73455589c19b1fc2f291bdeb4f9e8" | base64
```

## 🐛 문제 해결

### 일반적인 문제들

#### 1. Pod가 시작되지 않는 경우
```bash
# Pod 상태 확인
kubectl describe pod <pod-name> -n kangaroo-tine

# 이벤트 로그 확인
kubectl get events -n kangaroo-tine --sort-by='.lastTimestamp'
```

#### 2. 데이터베이스 연결 실패
```bash
# MySQL 로그 확인
kubectl logs -f deployment/mysql -n kangaroo-tine

# 네트워크 정책 확인
kubectl get networkpolicies -n kangaroo-tine
```

#### 3. 서비스 간 통신 문제
```bash
# 서비스 엔드포인트 확인
kubectl get endpoints -n kangaroo-tine

# DNS 확인
kubectl run -it --rm debug --image=busybox --restart=Never -- nslookup member-service
```

## 📊 모니터링

### 헬스체크 엔드포인트
- API Gateway: `http://{EXTERNAL-IP}:8080/actuator/health`
- Member Service: `http://{EXTERNAL-IP}:8080/api/member/health`
- Planner Service: `http://{EXTERNAL-IP}:8080/api/planner/health`
- Engagement Service: `http://{EXTERNAL-IP}:8080/api/engagement/health`

### 로그 수집
```bash
# 모든 서비스 로그 수집
kubectl logs -l app=member-service -n kangaroo-tine
kubectl logs -l app=planner-service -n kangaroo-tine
kubectl logs -l app=engagement-service -n kangaroo-tine
kubectl logs -l app=api-gateway -n kangaroo-tine
```

## 🔄 CI/CD 파이프라인

### SourcePipeline 설정
1. **네이버 클라우드 콘솔** → **SourcePipeline** → **파이프라인 생성**
2. **단계 구성**:
   - **소스 단계**: SourceCommit 연결
   - **빌드 단계**: SourceBuild 연결
   - **배포 단계**: SourceDeploy 연결

### 자동 배포 설정
```yaml
# .sourcepipeline.yml (예시)
stages:
  - name: build
    type: sourcebuild
    config:
      projectId: "your-build-project-id"
  - name: deploy
    type: sourcedeploy
    config:
      clusterId: "your-cluster-id"
      namespace: "kangaroo-tine"
```

## 📝 추가 리소스

- [네이버 클라우드 플랫폼 문서](https://guide.ncloud-docs.com/)
- [Kubernetes 공식 문서](https://kubernetes.io/docs/)
- [Spring Cloud Gateway 문서](https://spring.io/projects/spring-cloud-gateway)

## 🆘 지원

문제가 발생하거나 질문이 있으시면 다음을 통해 문의하세요:
- 이슈 트래커: GitHub Issues
- 이메일: support@kangarootine.com
