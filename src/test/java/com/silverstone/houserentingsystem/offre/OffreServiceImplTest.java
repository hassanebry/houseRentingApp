package com.silverstone.houserentingsystem.offre;

import com.silverstone.houserentingsystem.bien.Bien;
import com.silverstone.houserentingsystem.bien.BienRepository;
import com.silverstone.houserentingsystem.bien.adresse.Adresse;
import com.silverstone.houserentingsystem.bien.caracteristique.Caracteristique;
import com.silverstone.houserentingsystem.bien.enumeration.Energie;
import com.silverstone.houserentingsystem.bien.enumeration.Exposition;
import com.silverstone.houserentingsystem.bien.enumeration.TypeBien;
import com.silverstone.houserentingsystem.exceptions.BienInnexistantException;
import com.silverstone.houserentingsystem.exceptions.OffreInexistantException;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;
import com.silverstone.houserentingsystem.utilisateur.Role;
import com.silverstone.houserentingsystem.utilisateur.UserRepository;
import com.silverstone.houserentingsystem.utilisateur.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class OffreServiceImplTest {

    private OffreServiceImpl underTest;
    @Mock
    private OffreRepository offreRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BienRepository bienRepository;

    @Captor
    ArgumentCaptor<Offre> offreArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new OffreServiceImpl(offreRepository, userRepository, bienRepository);
    }

    @Test
    void caDoitDeposerUneOffre() throws BienInnexistantException, UtilisateurInexistantException {
        //Given
        OffreDto offreDto = new OffreDto(100L, "desc", LocalDate.of(2022, 04, 30), 1L, 1L);

        long userId = 1L;
        Utilisateur utilisateur = new Utilisateur(userId,"john", "jhon@test.com", "pwd", Role.OWNER, null, null, null);

        long bienId = 1L;
        Bien bien = new Bien(bienId,TypeBien.MAISON,
                new Adresse(1, "rue de test", 44300, "Nantes"), 100L,
                new Caracteristique(1992, true, true, true, true, Energie.A, Exposition.SUD),
                utilisateur, null);

        Offre offre = new Offre(offreDto.getPrix(), offreDto.getDescription(), offreDto.getDisponibleLe());
        offre.setUtilisateur(utilisateur);
        offre.setBienConcerne(bien);

        given(userRepository.findById(userId)).willReturn(Optional.of(utilisateur));
        given(bienRepository.findById(bienId)).willReturn(Optional.of(bien));

        //When
        underTest.deposerUneOffre(offreDto);

        //Then
        then(offreRepository).should().save(offreArgumentCaptor.capture());
        Offre value = offreArgumentCaptor.getValue();
        assertThat(value).isEqualToIgnoringGivenFields(offre, "deposeLe");
    }

    @Test
    void caDoitLeverUneExceptionUtilisateurEnDeposantUneOffre() throws BienInnexistantException, UtilisateurInexistantException {
        //Given
        OffreDto offreDto = new OffreDto(100L, "desc", LocalDate.of(2022, 04, 30), 1L, 1L);

        long userId = 1L;
        Utilisateur utilisateur = new Utilisateur(userId,"john", "jhon@test.com", "pwd", Role.OWNER, null, null, null);

        long bienId = 1L;
        Bien bien = new Bien(bienId,TypeBien.MAISON,
                new Adresse(1, "rue de test", 44300, "Nantes"), 100L,
                new Caracteristique(1992, true, true, true, true, Energie.A, Exposition.SUD),
                utilisateur, null);

        Offre offre = new Offre(offreDto.getPrix(), offreDto.getDescription(), offreDto.getDisponibleLe());
        offre.setUtilisateur(utilisateur);
        offre.setBienConcerne(bien);

        given(userRepository.findById(userId)).willReturn(Optional.empty());
        given(bienRepository.findById(bienId)).willReturn(Optional.of(bien));

        //When
        //Then
        assertThatThrownBy(()->underTest.deposerUneOffre(offreDto)).isInstanceOf(UtilisateurInexistantException.class);
        then(offreRepository).shouldHaveNoInteractions();
    }

    @Test
    void caDoitLeverUneExceptionBienEnDeposantUneOffre() throws BienInnexistantException, UtilisateurInexistantException {
        //Given
        OffreDto offreDto = new OffreDto(100L, "desc", LocalDate.of(2022, 04, 30), 1L, 1L);

        long userId = 1L;
        Utilisateur utilisateur = new Utilisateur(userId,"john", "jhon@test.com", "pwd", Role.OWNER, null, null, null);

        long bienId = 1L;
        Bien bien = new Bien(bienId,TypeBien.MAISON,
                new Adresse(1, "rue de test", 44300, "Nantes"), 100L,
                new Caracteristique(1992, true, true, true, true, Energie.A, Exposition.SUD),
                utilisateur, null);

        Offre offre = new Offre(offreDto.getPrix(), offreDto.getDescription(), offreDto.getDisponibleLe());
        offre.setUtilisateur(utilisateur);
        offre.setBienConcerne(bien);

        given(userRepository.findById(userId)).willReturn(Optional.of(utilisateur));
        given(bienRepository.findById(bienId)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.deposerUneOffre(offreDto)).isInstanceOf(BienInnexistantException.class);
        then(offreRepository).shouldHaveNoInteractions();
    }

    @Test
    void caDoitChercherUneOffreParId() throws OffreInexistantException {
        //Given
        long idOffre = 1L;
        Offre actual = new Offre(idOffre, 100L, "desc", LocalDateTime.now(), LocalDate.now(), LocalDate.now(), null, null, null);
        given(offreRepository.findById(idOffre)).willReturn(Optional.of(actual));

        //When
        Offre expected = underTest.chercherUneOffreParId(idOffre);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void caDoitLeverUneExceptionOffreEnCherchantUneOffreParId() throws OffreInexistantException {
        //Given
        long idOffre = 1L;
        Offre actual = new Offre(idOffre, 100L, "desc", LocalDateTime.now(), LocalDate.now(), LocalDate.now(), null, null, null);
        given(offreRepository.findById(idOffre)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.chercherUneOffreParId(idOffre)).isInstanceOf(OffreInexistantException.class);
        then(offreRepository).shouldHaveNoInteractions();
    }

    @Test
    void caDoitSupprimerUneOffre() {
        //Given
    }

    @Test
    void caDoitAfficherToutesLesOffres() {
        //Given
        long idOffre = 1L;
        Offre offre = new Offre(idOffre, 100L, "desc", LocalDateTime.now(), LocalDate.now(), LocalDate.now(), null, null, null);
        long idOffre1 = 1L;
        Offre offre1 = new Offre(idOffre1, 100L, "desc", LocalDateTime.now(), LocalDate.now(), LocalDate.now(), null, null, null);
        List<Offre> actuals = List.of(offre, offre1);
        given(offreRepository.findAll()).willReturn(actuals);

        //When
        Collection<Offre> results = underTest.afficherToutesLesOffres();

        //Then
        assertThat(actuals).isEqualTo(results);
    }
}