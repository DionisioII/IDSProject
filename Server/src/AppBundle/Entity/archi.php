<?php
// src/AppBundle/Entity/archi.php


namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;


/**
 * @ORM\Entity
 * @ORM\Table(name="archi")
 */
class archi
{


        /**
     * @ORM\Column(type="integer")
     * @ORM\Id
         */
    private $id_map;


    /**
     * @ORM\Column(type="string", length=25)
     * @ORM\Id
         */
    private $nodo1;

        /**
     * @ORM\Column(type="string", length=25)
     * @ORM\Id
         */
    private $nodo2;

   /**
     * @ORM\Column(type="float")
     */
    private $K;


   /**
     * @ORM\Column(type="float")
     */
    private $V;

   /**
     * @ORM\Column(type="float")
     */
    private $Plos;

   /**
     * @ORM\Column(type="float")
     */
    private $C;

   /**
     * @ORM\Column(type="float")
     */
    private $Pv;
   /**
     * @ORM\Column(type="integer")
     */
    private $area;

   /**
     * @ORM\Column(type="float")
     */
    private $Pi;

    /**
      * @ORM\Column(type="float")
      */
     private $Pc;

/**
     * @ORM\Column(type="float")
     */
    private $Los;

   /**
     * @ORM\Column(type="float")
     */
private $I;

   /**
     * @ORM\Column(type="integer")
     */
private $lunghezza;

    /**
     * Set idMap
     *
     * @param integer $idMap
     *
     * @return archi
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
     * Set nodo1
     *
     * @param string $nodo1
     *
     * @return archi
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
     * @return archi
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

    /**
     * Set k
     *
     * @param \float $k
     *
     * @return archi
     */
    public function setK($k)
    {
        $this->K = $k;

        return $this;
    }

    /**
     * Get k
     *
     * @return \float
     */
    public function getK()
    {
        return $this->K;
    }

    /**
     * Set v
     *
     * @param \float $v
     *
     * @return archi
     */
    public function setV(\float $v)
    {
        $this->V = $v;

        return $this;
    }

    /**
     * Get v
     *
     * @return \float
     */
    public function getV()
    {
        return $this->V;
    }

    /**
     * Set plos
     *
     * @param \float $plos
     *
     * @return archi
     */
    public function setPlos(\float $plos)
    {
        $this->Plos = $plos;

        return $this;
    }

    /**
     * Get plos
     *
     * @return \float
     */
    public function getPlos()
    {
        return $this->Plos;
    }

    /**
     * Set c
     *
     * @param \float $c
     *
     * @return archi
     */
    public function setC(\float $c)
    {
        $this->C = $c;

        return $this;
    }

    /**
     * Get c
     *
     * @return \float
     */
    public function getC()
    {
        return $this->C;
    }

    /**
     * Set pv
     *
     * @param \float $pv
     *
     * @return archi
     */
    public function setPv(\float $pv)
    {
        $this->Pv = $pv;

        return $this;
    }

    /**
     * Get pv
     *
     * @return \float
     */
    public function getPv()
    {
        return $this->Pv;
    }

    /**
     * Set area
     *
     * @param integer $area
     *
     * @return archi
     */
    public function setArea($area)
    {
        $this->area = $area;

        return $this;
    }

    /**
     * Get area
     *
     * @return integer
     */
    public function getArea()
    {
        return $this->area;
    }

    /**
     * Set pi
     *
     * @param \float $pi
     *
     * @return archi
     */
    public function setPi(\float $pi)
    {
        $this->Pi = $pi;

        return $this;
    }

    /**
     * Set pc
     *
     * @param \float $pc
     *
     * @return archi
     */
    public function setPc(\float $pc)
    {
        $this->Pc = $pc;

        return $this;
    }

    /**
     * Get pi
     *
     * @return \float
     */
    public function getPi()
    {
        return $this->Pi;
    }

    /**
     * Get pc
     *
     * @return \float
     */
    public function getPc()
    {
        return $this->Pc;
    }


    /**
     * Set los
     *
     * @param \float $los
     *
     * @return archi
     */
    public function setLos($los)
    {
        $this->Los = $los;

        return $this;
    }

    /**
     * Get los
     *
     * @return \float
     */
    public function getLos()
    {
        return $this->Los;
    }

    /**
     * Set i
     *
     * @param \float $i
     *
     * @return archi
     */
    public function setI(\float $i)
    {
        $this->I = $i;

        return $this;
    }

    /**
     * Get i
     *
     * @return \float
     */
    public function getI()
    {
        return $this->I;
    }

    /**
     * Set lunghezza
     *
     * @param integer $lunghezza
     *
     * @return archi
     */
    public function setLunghezza($lunghezza)
    {
        $this->lunghezza = $lunghezza;

        return $this;
    }

    /**
     * Get lunghezza
     *
     * @return integer
     */
    public function getLunghezza()
    {
        return $this->lunghezza;
    }
}
