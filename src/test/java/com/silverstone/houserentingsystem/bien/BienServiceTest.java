package com.silverstone.houserentingsystem.bien;

import com.silverstone.houserentingsystem.bien.adresse.Adresse;
import com.silverstone.houserentingsystem.bien.adresse.AdresseRepo;
import com.silverstone.houserentingsystem.bien.caracteristique.Caracteristique;
import com.silverstone.houserentingsystem.bien.caracteristique.CaracteristiqueRepo;
import com.silverstone.houserentingsystem.bien.enumeration.Energie;
import com.silverstone.houserentingsystem.bien.enumeration.Exposition;
import com.silverstone.houserentingsystem.bien.enumeration.TypeBien;
import com.silverstone.houserentingsystem.exceptions.BienInnexistantException;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;
import com.silverstone.houserentingsystem.utilisateur.UserRepository;
import com.silverstone.houserentingsystem.utilisateur.Utilisateur;
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

class BienServiceTest {

    private BienService underTest;
    @Mock
    private BienRepository bienRepository;
    @Captor
    ArgumentCaptor<Bien> bienArgumentCaptor;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AdresseRepo adresseRepo;

    @Captor
    ArgumentCaptor<Adresse> adresseArgumentCaptor;
    @Mock
    private CaracteristiqueRepo caracteristiqueRepo;
    @Captor
    ArgumentCaptor<Caracteristique> caracteristiqueArgumentCaptor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new BienService(bienRepository, userRepository, adresseRepo, caracteristiqueRepo);
    }

    @Test
    void CaDoitEnregistrerUnBien() throws UtilisateurInexistantException {
        //Given
        TypeBien type = TypeBien.MAISON;
        int numRue = 1;
        String rue = "Rue de La coliniere";
        int codepostal = 44300;
        String commune = "Nantes";
        Adresse adresse = new Adresse(numRue, rue, codepostal, commune);
        int anneeConstruction = 2022;
        boolean balcon = true;
        boolean cave = true;
        boolean ascenseur = true;
        Energie energie = Energie.A;
        boolean gardien = true;
        Exposition expo = Exposition.SUD;
        Caracteristique caracteristique = new Caracteristique(anneeConstruction, balcon, cave, ascenseur, gardien, energie, expo);
        long userId = 1L;
        long surface = 100L;
        BienDto bienDto = new BienDto(type, adresse, surface, caracteristique, userId);
        Utilisateur utilisateur = new Utilisateur("Lamar", "lamar@gmail.com", "mdp", "OWNER");
        Bien bien = new Bien(type, adresse, surface, caracteristique, utilisateur);

        given(userRepository.findById(userId)).willReturn(Optional.of(utilisateur));
        given(adresseRepo.findByRueAndCodepostalAndCommune(rue, codepostal, commune)).willReturn(Optional.empty());
        given(caracteristiqueRepo.findByAllField(anneeConstruction, balcon,cave,ascenseur,gardien, energie, expo)).willReturn(Optional.empty());

        //When
        underTest.enregistrerBien(bienDto);

        //Then
        then(adresseRepo).should().save(adresseArgumentCaptor.capture());
        Adresse adresseCaptored = adresseArgumentCaptor.getValue();
        assertThat(adresseCaptored).isEqualTo(adresse);

        then(caracteristiqueRepo).should().save(caracteristiqueArgumentCaptor.capture());
        Caracteristique caracteristiqueCaptored = caracteristiqueArgumentCaptor.getValue();
        assertThat(caracteristiqueCaptored).isEqualTo(caracteristique);

        then(bienRepository).should().save(bienArgumentCaptor.capture());
        Bien bienCaptored = bienArgumentCaptor.getValue();
        assertThat(bienCaptored).isEqualTo(bien);
    }

    @Test
    void CaDoitEnregistrerUnBienAvecAdresseEtCaracExistant() throws UtilisateurInexistantException {
        //Given
        TypeBien type = TypeBien.MAISON;
        int numRue = 1;
        String rue = "Rue de La coliniere";
        int codepostal = 44300;
        String commune = "Nantes";
        Adresse adresse = new Adresse(numRue, rue, codepostal, commune);
        int anneeConstruction = 2022;
        boolean balcon = true;
        boolean cave = true;
        boolean ascenseur = true;
        Energie energie = Energie.A;
        boolean gardien = true;
        Exposition expo = Exposition.SUD;
        Caracteristique caracteristique = new Caracteristique(anneeConstruction, balcon, cave, ascenseur, gardien, energie, expo);
        long userId = 1L;
        long surface = 100L;
        BienDto bienDto = new BienDto(type, adresse, surface, caracteristique, userId);
        Utilisateur utilisateur = new Utilisateur("Lamar", "lamar@gmail.com", "mdp", "OWNER");
        Bien bien = new Bien(type, adresse, surface, caracteristique, utilisateur);

        given(userRepository.findById(userId)).willReturn(Optional.of(utilisateur));
        given(adresseRepo.findByRueAndCodepostalAndCommune(rue, codepostal, commune)).willReturn(Optional.of(adresse));
        given(caracteristiqueRepo.findByAllField(anneeConstruction, balcon,cave,ascenseur,gardien, energie, expo)).willReturn(Optional.of(caracteristique));

        //When
        underTest.enregistrerBien(bienDto);

        //Then
        assertThat(adresse).isEqualTo(adresse);

        assertThat(caracteristique).isEqualTo(caracteristique);

        then(bienRepository).should().save(bienArgumentCaptor.capture());
        Bien bienCaptored = bienArgumentCaptor.getValue();
        assertThat(bienCaptored).isEqualTo(bien);
    }

    @Test
    void CaNeDoitPasEnregistrerUnBien() throws UtilisateurInexistantException {
        //Given
        TypeBien type = TypeBien.MAISON;
        Adresse adresse = new Adresse(1, "Rue de La coliniere", 44300, "Nantes");
        Caracteristique caracteristique = new Caracteristique(2022, true, true, true, true, Energie.A, Exposition.SUD);
        long userId = 1L;
        long surface = 100L;
        BienDto bienDto = new BienDto(type, adresse, surface, caracteristique, userId);
        Utilisateur utilisateur = new Utilisateur("Lamar", "lamar@gmail.com", "mdp", "OWNER");
        Bien bien = new Bien(type, adresse, surface, caracteristique, utilisateur);

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        //When
        assertThatThrownBy(()->underTest.enregistrerBien(bienDto))
                .isInstanceOf(UtilisateurInexistantException.class);

        //Then
        then(adresseRepo).shouldHaveNoInteractions();

        then(caracteristiqueRepo).shouldHaveNoInteractions();

        then(bienRepository).shouldHaveNoInteractions();
    }

    @Test
    void caDoitAfficherBienParId() throws BienInnexistantException {
        //Given
        TypeBien type = TypeBien.MAISON;
        Adresse adresse = new Adresse(1, "Rue de La coliniere", 44300, "Nantes");
        Caracteristique caracteristique = new Caracteristique(2022, true, true, true, true, Energie.A, Exposition.SUD);
        long userId = 1L;
        long surface = 100L;
        Utilisateur utilisateur = new Utilisateur("Lamar", "lamar@gmail.com", "mdp", "OWNER");

        long bienId = 1L;
        Bien bie = new Bien(bienId, type, adresse, surface, caracteristique, utilisateur, null);
        given(bienRepository.findById(userId)).willReturn(Optional.of(bie));

        //When
        Bien result = underTest.afficherBienParId(bienId);

        //Then
        assertThat(result).isEqualTo(bie);
    }

    @Test
    void caNeDoitPasAfficherBienParId() throws BienInnexistantException {
        //Given
        TypeBien type = TypeBien.MAISON;
        Adresse adresse = new Adresse(1, "Rue de La coliniere", 44300, "Nantes");
        Caracteristique caracteristique = new Caracteristique(2022, true, true, true, true, Energie.A, Exposition.SUD);
        long userId = 1L;
        long surface = 100L;
        Utilisateur utilisateur = new Utilisateur("Lamar", "lamar@gmail.com", "mdp", "OWNER");

        long bienId = 1L;
        Bien bie = new Bien(bienId, type, adresse, surface, caracteristique, utilisateur, null);
        given(bienRepository.findById(userId)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.afficherBienParId(bienId)).isInstanceOf(BienInnexistantException.class);
    }

    @Test
    void caDoitAfficherTousLesBiens() {
        TypeBien type = TypeBien.MAISON;
        Adresse adresse = new Adresse(1, "Rue de La coliniere", 44300, "Nantes");
        Caracteristique caracteristique = new Caracteristique(2022, true, true, true, true, Energie.A, Exposition.SUD);
        long userId = 1L;
        long surface = 100L;
        Utilisateur utilisateur = new Utilisateur("Lamar", "lamar@gmail.com", "mdp", "OWNER");

        long bienId = 1L;
        Bien bien = new Bien(bienId, type, adresse, surface, caracteristique, utilisateur, null);
        Bien bien1 = new Bien(bienId, type, adresse, surface, caracteristique, utilisateur, null);
        List<Bien> biens = List.of(bien, bien1);
        given(bienRepository.findAll()).willReturn(biens);

        //When
        List<Bien> resltatBiens = underTest.afficherTousLesBiens();

        //Then
        assertThat(biens).isEqualTo(resltatBiens);
    }
}