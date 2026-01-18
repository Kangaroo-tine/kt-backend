# KangarooTine MSA Backend

## 프로젝트 개요
KangarooTine은 MSA(Microservice Architecture) 기반의 백엔드 시스템으로, 네이버 클라우드 플랫폼의 Kubernetes 환경에 배포됩니다.

## 아키텍처 구성

### 서비스 구성
- **API Gateway** (`api-gw/`): 외부 요청의 진입점 (포트: 8080)
- **Member Service** (`member-service/`): 사용자 인증 및 관리 (포트: 8081)
- **Planner Service** (`planner-service/`): 목표 관리 서비스 (포트: 8082)
- **Engagement Service** (`engagement-service/`): AI Kelper 및 일정 서비스 (포트: 8083)

### 데이터베이스 구성
- **MySQL 8.0**: 단일 서버에서 논리적 스키마 분리
  - `member_db`: 사용자 관련 데이터
  - `planner_db`: 목표 관련 데이터
  - `engagement_db`: AI Kelper 및 일정 관련 데이터

## 기술 스택
- **Backend**: Spring Boot 3.4.5, Java 17
- **Database**: MySQL 8.0
- **Security**: Spring Security, JWT
- **API Documentation**: Swagger/OpenAPI
- **Container**: Docker
- **Orchestration**: Kubernetes
- **Cloud Platform**: 네이버 클라우드 플랫폼

## 프로젝트 구조
```
kt-backend/
├── api-gw/                    # API Gateway 서비스
├── member-service/            # 사용자 인증 및 관리 서비스
├── planner-service/           # 일정 관리 서비스
├── engagement-service/        # 참여 및 소통 서비스
├── k8s-manifests/            # Kubernetes 배포 매니페스트
│   ├── namespace.yaml
│   ├── secrets.yaml
│   ├── configmap.yaml
│   ├── pvc.yaml
│   ├── mysql.yaml
│   ├── mysql-init-script.yaml
│   ├── member-service.yaml
│   ├── planner-service.yaml
│   ├── engagement-service.yaml
│   └── api-gateway.yaml
├── docker-compose.yml         # 로컬 개발 환경용
├── DEPLOYMENT_GUIDE.md       # 배포 가이드
└── README.md
```

## 빠른 시작

### 로컬 개발 환경 실행
```bash
# Docker Compose로 모든 서비스 실행
docker-compose up -d

# 서비스 상태 확인
docker-compose ps
```

### Kubernetes 배포
자세한 배포 방법은 [DEPLOYMENT_GUIDE.md](./DEPLOYMENT_GUIDE.md)를 참조하세요.

## 환경변수 설정

### 필수 시크릿 값들
- `DB_USERNAME`: MySQL 사용자명
- `DB_PASSWORD`: MySQL 비밀번호
- `JWT_SECRET`: JWT 토큰 서명용 시크릿 키
- `KAKAO_REST_API_KEY`: 카카오 API 키 (Member Service용)

### 설정 방법
1. `k8s-manifests/secrets.yaml`에서 base64 인코딩된 값들을 실제 값으로 변경
2. `k8s-manifests/configmap.yaml`에서 애플리케이션 설정 조정

## API 접근 방법

### 로컬 환경
- API Gateway: http://localhost:8080
- Member Service: http://localhost:8081
- Planner Service: http://localhost:8082
- Engagement Service: http://localhost:8083

### Kubernetes 환경
- API Gateway를 통한 통합 접근: `http://<EXTERNAL-IP>:8080/api/{service}`
- 각 서비스별 직접 접근: 포트 포워딩 사용

## 개발 가이드

### 서비스 추가
1. 새로운 서비스 폴더 생성
2. `build.gradle`, `Dockerfile` 생성
3. Kubernetes 매니페스트 파일 생성
4. API Gateway 라우팅 설정 추가

### 데이터베이스 스키마 추가
1. `k8s-manifests/mysql-init-script.yaml`에 새 스키마 추가
2. 서비스별 `application.yml`에서 데이터베이스 URL 설정

## 모니터링 및 로그

### 헬스체크
모든 서비스는 `/actuator/health` 엔드포인트를 제공합니다.

### 로그 확인
```bash
# Kubernetes 환경
kubectl logs -f deployment/<service-name> -n kangaroo-tine

# Docker Compose 환경
docker-compose logs -f <service-name>
```