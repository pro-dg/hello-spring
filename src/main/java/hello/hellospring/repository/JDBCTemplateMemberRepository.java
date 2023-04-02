package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JDBCTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    // 아래와 같이 생성자가 단 하나일 경우엔 @Autowired를 생략해도 된다.
    @Autowired
    public JDBCTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = Map.of("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(parameters);
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id)
                .stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name)
                .stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }


    public void clearStore() {
        jdbcTemplate.update("delete from member");
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
