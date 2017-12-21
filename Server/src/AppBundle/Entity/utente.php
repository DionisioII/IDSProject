<?php
// src/AppBundle/Entity/utente.php
namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\Common\Annotations\AnnotationReader;


/**
* @ORM\Entity
* @ORM\Table(name="utente")
*/
class utente
{


  /**
  * @ORM\Column(type="integer")
  * @ORM\Id
  * @ORM\GeneratedValue(strategy="AUTO")
  */
  private $id_utente;


  /**
  * @ORM\Column(type="text")
  * @Assert\NotBlank(
  * message = "nome non puo essere vuoto.",
  * )
  */
  private $nome;

  /**
  * @ORM\Column(type="text")
  * @Assert\NotBlank(
  * message = "cognome non puo essere vuoto.",
  * )
  */
  private $cognome;



  /**
  * @ORM\Column(type="text")
  * @Assert\NotBlank(
  * message = "password non puo essere vuota.",
  * )
  */
  private $password;

  /**
  * @ORM\Column(type="text")
  * @Assert\Email(
  * message = "Email '{{ value }}' non valida.",
  * checkMX = true
  * )
  */
  private $email;



  /**
  * @ORM\Column(type="text")
  *
  */
  private $token;


  /**
  * Set token
  *
  * @param string $token
  *
  * @return utente
  */
  public function setToken($token)
  {
    $this->token = $token;

    return $this;
  }


  /**
  * Get token
  *
  * @return string
  */
  public function getToken()
  {
    return $this->token;
  }


  /**
  * Get idUtente
  *
  * @return integer
  */
  public function getIdUtente()
  {
    return $this->id_utente;
  }

  /**
  * Set nome
  *
  * @param string $nome
  *
  * @return utente
  */
  public function setNome($nome)
  {
    $this->nome = $nome;

    return $this;
  }

  /**
  * Get nome
  *
  * @return string
  */
  public function getNome()
  {
    return $this->nome;
  }

  /**
  * Set cognome
  *
  * @param string $cognome
  *
  * @return utente
  */
  public function setCognome($cognome)
  {
    $this->cognome = $cognome;

    return $this;
  }

  /**
  * Get cognome
  *
  * @return string
  */
  public function getCognome()
  {
    return $this->cognome;
  }


  /**
  * Set password
  *
  * @param string $password
  *
  * @return utente
  */
  public function setPassword($password)
  {
    $this->password = $password;

    return $this;
  }

  /**
  * Get password
  *
  * @return string
  */
  public function getPassword()
  {
    return $this->password;
  }

  /**
  * Set email
  *
  * @param string $email
  *
  * @return utente
  */
  public function setEmail($email)
  {
    $this->email = $email;

    return $this;
  }

  /**
  * Get email
  *
  * @return string
  */
  public function getEmail()
  {
    return $this->email;
  }


}
