<?php
// src/AppBundle/Entity/localizzazioni.php


namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;


/**
 * @ORM\Entity
 * @ORM\Table(name="localizzazioni")
 */
class localizzazioni
{



    /**
      * @ORM\Column(type="text")
      *@ORM\Id
      */
    private  $id_utente;

    /**
      * @ORM\Column(type="integer")
      */
    private $id_map;




    /**
      * @ORM\Column(type="text")
      */
    private $nodo1;
      /**
          * @ORM\Column(type="text")
          */
        private $nodo2;







    /**
     * Set idMap
     *
     * @param integer $idMap
     *
     * @return localizzazioni
     */
    public function setIdMap($idMap)
    {
        $this->id_map = $idMap;

        return $this;
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
    * Set idUtente
    *
    * @param integer $cognome
    *
    * @return utente
    */
    public function setIdUtente($id_utente)
    {
      $this->id_utente = $id_utente;

      return $this;
    }


    /**
     * Get idMap
     *
     * @return integer
     */
    public function getIdMap()
    {
        return $this->id_map;
    }

    /**
     * Set nodo1
     *
     * @param string $nodo1
     *
     * @return localizzazioni
     */
    public function setNodo1($nodo1)
    {
        $this->nodo1 = $nodo1;

        return $this;
    }

    /**
     * Get nodo1
     *
     * @return string
     */
    public function getNodo1()
    {
        return $this->nodo1;
    }

    /**
     * Set nodo2
     *
     * @param string $nodo2
     *
     * @return localizzazioni
     */
    public function setNodo2($nodo2)
    {
        $this->nodo2 = $nodo2;

        return $this;
    }

    /**
     * Get nodo2
     *
     * @return string
     */
    public function getNodo2()
    {
        return $this->nodo2;
    }
}
