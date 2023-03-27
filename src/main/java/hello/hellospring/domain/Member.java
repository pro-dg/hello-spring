package hello.hellospring.domain;

public class Member {

    private Long id; // 시스템이 저장할 때 사용
    private String name; // 사용자가 입력한 이름

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
