package com.ticket.Service;

import com.ticket.Dto.EmailDto;
import com.ticket.Dto.UserCreateRequestDto;
import com.ticket.Exception.UserNotFoundException;
import com.ticket.Model.User;
import com.ticket.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final AmqpTemplate rabbitTemplate;

    public UserService(UserRepository userRepository, AmqpTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public User createUser(UserCreateRequestDto userCreateRequestDto){
        User user= new User();
        user.setName(userCreateRequestDto.getName());
        user.setUserType(userCreateRequestDto.getUserType());
        user.setPassword(userCreateRequestDto.getPassword());
        user.setEmail(userCreateRequestDto.getEmail());
        user.setMobilePhone(userCreateRequestDto.getMobilePhone());
        user.setRole(userCreateRequestDto.getRole());
        user.setGender(userCreateRequestDto.getGender());
        hashing(user);//metot ile şifreleme yapıldı
        //kayıt başarılı ise
        userRepository.save(user);
        log.info(user.getName()+ " isimli müşterinin kaydı gerçekleştirilmiştir");
        try{

            log.info(user.getName()+ " isimli müşterinin kaydı gerçekleştirilmiştir");
            //yeni müşteri eklendiğinde mail gönderilsin
            rabbitTemplate.convertAndSend("${rabbitmq.queue}",
                    new EmailDto(user.getEmail(),
                            "yeni müşteri eklendi",
                            "bilet uygulamamıza hoşgeldiniz","Email"));
            log.info(user.getEmail()+"  mailiniz gönderilmiştir");
            return user;
        }
        //daha önceden kayıtlı ise
        catch (Exception e){
            log.info(user.getName()+" isimli müşteri daha önceden kayıt olmuştur");
            return user;
        }
    }
    //hashing algoritması ile şifrelenmenin yapılması
    public void hashing(User userRequest){
        String sha256Pass = org.apache.commons.codec.digest.DigestUtils.sha256Hex(userRequest.getPassword());
        userRequest.setPassword(sha256Pass);
    }
    //eğer admin ise eklediği seferler gözükecektir
    //user ise aldığı biletler gözükecektir
    public User getByUserId(Integer userId){
        return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("kullanıcı bulunamadı"));
    }
    public User updateUser(Integer idRequest,UserCreateRequestDto userCreateRequestDto) {
        User user1=userRepository.findById(idRequest).orElseThrow(()->new UserNotFoundException("kullanıcı bulunamadı"));
        user1.setName(userCreateRequestDto.getName());
        user1.setPassword(userCreateRequestDto.getPassword());
        hashing(user1);
        userRepository.save(user1);
        return user1;
    }

    public User deleteUser(Integer userId) {
        User user1=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("kullanıcı bulunamadı"));
        userRepository.delete(user1);
        return user1;
    }

    /*public List<Ticket> getListMyTickets(String userName) {
        User user1=userRepository.findByName(userName).get();
        return user1.getTicketList();
    }*/

}
