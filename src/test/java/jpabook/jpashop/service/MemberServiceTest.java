package jpabook.jpashop.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

// @ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;
	// @Autowired
	// EntityManager em;

	@Test
	// @Rollback(false)
	public void 회원가입() throws Exception {
		//given
		Member member = new Member();
		member.setName("kim");

		//when
		Long savedId = memberService.join(member);

		//then
		// em.flush();
		assertEquals(member, memberRepository.findOne(savedId));

	}

	@Test
	public void 중복_회원_예외() throws Exception {
		//given
		Member member1 = new Member();
		member1.setName("kim1");

		Member member2 = new Member();
		member2.setName("kim1");

		//when
		memberService.join(member1);
		assertThatThrownBy(() -> {
			memberService.join(member2); //예외가 발생해야 한다!!!
		}).isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("존재하는");

		//then
		// AssertionsForClassTypes.fail("예외가 발생해야 한다."); //쓰려면 앞 부분에 빈 return 추가
	}
}