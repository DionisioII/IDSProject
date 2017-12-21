<?php
// src/AppBundle/Entity/emergenze.php


namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;


/**
 * @ORM\Entity
 * @ORM\Table(name="emergenze")
 */
class emergenze
{

  /**
     * @ORM\Column(type="integer")
     * @ORM\Id
         */
    private $id_emergenza;



    /**
      * @ORM\Column(type="boolean")
      */
    private $stato;


    /**
      * @ORM\Column(type="string")
      */
    private $descrizione;




    /**
     * Set idEmergenze
     *
     * @param integer $idEmergenza
     *
     * @return emergenze
     */
    public function setIdEmergenza($idEmergenza)
    {
        $this->id_emergenza = $idEmergenza;

        return $this;
    }

    /**
     * Get idEmergenza
     *
     * @return integer
     */
    public function getIdEmergenza()
    {
        return $this->id_emergenza;
    }

    /**
     * Set stato
     *
     * @param boolean $stato
     *
     * @return emergenze
     */
    public function setStato($stato)
    {
        $this->stato = $stato;

        return $this;
    }

    /**
     * Get stato
     *
     * @return boolean
     */
    public function getStato()
    {
        return $this->stato;
    }

    /**
     * Set descrizione
     *
     * @param string $descrizione
     *
     * @return emergenze
     */
    public function setDescrizione($descrizione)
    {
        $this->descrizione = $descrizione;

        return $this;
    }

    /**
     * Get descrizione
     *
     * @return string
     */
    public function getDescrizione()
    {
        return $this->descrizione;
    }
}
