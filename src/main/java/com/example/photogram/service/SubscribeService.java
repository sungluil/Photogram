package com.example.photogram.service;

import com.example.photogram.domain.Subscribe;
import com.example.photogram.domain.User;
import com.example.photogram.domain.UserDTO;
import com.example.photogram.dto.SubscribeDTO;
import com.example.photogram.handler.CustomApiException;
import com.example.photogram.mapper.SubscribeDTOMapper;
import com.example.photogram.mapper.UserDTOMapper;
import com.example.photogram.repository.SubscribeRepository;
import com.example.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final UserRepository userRepository;
    private final SubscribeDTOMapper subscribeDTOMapper;
    private final UserDTOMapper userDTOMapper;

    private final EntityManager em; // Repository는 EntityManager를 구현해서 만들어져 있는 구현체

    @Transactional(readOnly = true)
    public List<SubscribeDTO> 구독리스트(Long principalId, int pageUserId){

        // 쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
        sb.append("if ((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?"); // 세미콜론 첨부하면 안됨

        // 1.물음표 principalId
        // 2.물음표 principalId
        // 3.물음표 pageUserId

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        // 쿼리 실행 (qlrm 라이브러리 필요 = DTO에 DB결과를 매핑하기 위해서)
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDTO> subscribeDtos =  result.list(query, SubscribeDTO.class);

        return subscribeDtos;
    }

    @Transactional
    public void 구독하기(Long fromUserId, Long toUserId) {
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독을 하였습니다.");
        }
    }

    @Transactional
    public void 구독취소하기(Long fromUserId, Long toUserId) {
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }
}
