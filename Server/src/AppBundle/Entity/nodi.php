<?php
namespace AppBundle\Entity;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="nodi")
 */
class nodi
{    /**
     * @ORM\Column(type="string", length=25)
     * @ORM\Id
     */
    private $id_nodo;
    /**
     * @ORM\Column(type="integer")
     */
    private $cordX;
     /**
     * @ORM\Column(type="integer")
     */
    private $cordY;
     /**
     * @ORM\Column(type="integer")
     */
    private $id_map;

    /**
    * @ORM\Column(type="integer")
    */
   private $uscita;


    /**
     * Set idNodo
     *
     * @param string $idNodo
     *
     * @return nodi
     */
    public function setIdNodo($idNodo)
    {
        $this->id_nodo = $idNodo;

        return $this;
    }

    /**
     * Get idNodo
     *
     * @return string
     */
    public function getIdNodo()
    {
        return $this->id_nodo;
    }

    /**
     * Set cordX
     *
     * @param integer $cordX
     *
     * @return nodi
     */
    public function setCordX($cordX)
    {
        $this->cordX = $cordX;

        return $this;
    }

    /**
     * Get cordX
     *
     * @return integer
     */
    public function getCordX()
    {
        return $this->cordX;
    }

    /**
     * Set cordY
     *
     * @param integer $cordY
     *
     * @return nodi
     */
    public function setCordY($cordY)
    {
        $this->cordY = $cordY;

        return $this;
    }

    /**
     * Get cordY
     *
     * @return integer
     */
    public function getCordY()
    {
        return $this->cordY;
    }

    /**
     * Set idMap
     *
     * @param integer $idMap
     *
     * @return nodi
     */
    public function setIdMap($idMap)
    {
        $this->id_map = $idMap;

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
     * Set uscita
     *
     * @param integer $uscita
     * @return nodi
     */
    public function setUscita($uscita)
    {
        $this->uscita = $uscita;

        return $this;
    }

    /**
     * Get uscita
     *
     * @return integer
     */
    public function getUscita()
    {
        return $this->uscita;
    }


}
