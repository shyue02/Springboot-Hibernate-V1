package site.metacoding.white.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;

@RequiredArgsConstructor // 생성자 주입 / 없으면 디폴트생성자
@Service // -> 붙여야 IoC에 뜬다
public class UserService {

    private final UserRepository userRepository;

    @Transactional // -> 트랜잭션이 붙어있지 않으면 영속화 되어 있는 객체가 flush가 안 됨.
    public void save(User user) {
        userRepository.save(user); // 얘가 save를 board로 한다면 컨트롤러한테 보드 받아야한다, 컨트롤러는 클라이언트한테 받아야 한다
    }

    @Transactional(readOnly = true)
    public User login(User user) {
        User userPS = userRepository.findByUsername(user.getUsername());
        if (userPS.getPassword().equals(user.getPassword())) {
            return userPS;
        } else {
            throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력되었습니다.");
        }
    }// 트랜잭션 종료

}
