### 20211020
- 회원가입
  - email 중복 체크(백 -> 프론트)
  - 비밀번호 이중 체크(프론트)
  - email 인증
  - 소셜 로그인(git, google..정도)
- HttpSession을 이용한 로그인/로그아웃 기능 구현
  - Session 타임아웃 (Default: 30min. -> 1h.)
  - Session URL Rewriting 방지
  ````xml
  server.servlet.session.timeout=3600
  server.servlet.session.tracking-modes=cookie
  ````