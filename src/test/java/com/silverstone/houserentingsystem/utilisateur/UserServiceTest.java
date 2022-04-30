package com.silverstone.houserentingsystem.utilisateur;

import com.silverstone.houserentingsystem.exceptions.UtilisateurAlreadyExists;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class UserServiceTest {

    private UserService underTest;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<Utilisateur> utilisateurArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new UserService(userRepository);
    }

    @Test
    void itShouldGetAllUsers() {
        //Given
        Utilisateur u1 = new Utilisateur("lamar", "lamar@gmail.com", "mdp", "customer");
        Utilisateur u2 = new Utilisateur("idiat", "idia@gmail.com", "mdp", "customer");
        List<Utilisateur> mesUsers = List.of(u1, u2);

        given(userRepository.findAll()).willReturn(mesUsers);

        //When
        List<Utilisateur> allUsers = underTest.getAllUsers();

        //Then
        assertThat(allUsers).isEqualTo(mesUsers);
    }

    @Test
    void itShouldGetUserByEmail() throws UtilisateurInexistantException {
        //Given
        String email = "lamar@gmail.com";
        Utilisateur utilisateur = new Utilisateur("lamar", email, "mdp", "customer");
        given(userRepository.findByEmail(email)).willReturn(Optional.of(utilisateur));

        //When
        Utilisateur utilisateur1 = underTest.getUserByEmail(email);
        //Then
        assertThat(utilisateur).isNotNull();
        assertThat(utilisateur1).isEqualTo(utilisateur);
    }

    @Test
    void itShouldNotGetUserByEmail() throws UtilisateurInexistantException {
        //Given
        String email = "lamar@gmail.com";
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.getUserByEmail(email))
                .isInstanceOf(UtilisateurInexistantException.class);
    }

    @Test
    void itShouldsaveUser() throws UtilisateurAlreadyExists {
        //Given
        String email = "lamar@gmail.com";
        UserRequest userRequest = new UserRequest("lamar", email, "mdp", "customer");

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        Utilisateur utilisateur = new Utilisateur(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword(), userRequest.getRole());

        //When
        underTest.saveUser(userRequest);
        //Then
        then(userRepository).should().save(utilisateurArgumentCaptor.capture());
        Utilisateur value = utilisateurArgumentCaptor.getValue();
        assertThat(value).isEqualTo(utilisateur);
    }

    @Test
    void itShouldThrowUserAlreadyExist(){
        //Given
        String email = "lamar@gmail.com";
        UserRequest userRequest = new UserRequest("lamar", email, "mdp", "customer");
        Utilisateur utilisateur = new Utilisateur(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword(), userRequest.getRole());

        given(userRepository.findByEmail(email)).willReturn(Optional.of(utilisateur));

        //When
        //Then
        assertThatThrownBy(()->underTest.saveUser(userRequest))
                .isInstanceOf(UtilisateurAlreadyExists.class)
                .hasMessage("this user already exist");
    }

}