> ## Spring 정리



- 컨트롤러 : 웹 MVC의 컨트롤러 역할
- 서비스 : 핵심 비즈니스 로직 구현
- 리포지토리 : 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
- 도메인 : 비즈니스 도메인 객체, 예) 회원, 주문, 쿠폰 등등 주로 데이터베이스에 저장하고 관리됨



- 개발 단계에서 실제 데이터 베이스에 연결되지 않았다면, 가상 메모리 공간에 데이터를 생성 후 테스트



```
Object.isPresent : 객체에 값이 있으면 true, 없으면 false 반환
Object.ifPresent : 객체에 값이 있으면 실행 없으면 넘어감

// result 가 존재 => 같은 name을 가진 Member 객체가 이미 존재한다.
Optional<Member> result = memberRepository.findByName(member.getName());
result.ifPresent(m -> {
    throw new IllegalStateException("이미 존재하는 회원입니다.");
});

// Optional 을 바로 사용하는 것을 권장하지 않기 때문에 아래와 같이 변경

memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
```



> ###  회원 기능 구현 예제