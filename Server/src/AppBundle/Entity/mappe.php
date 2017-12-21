<?php
// src/AppBundle/Entity/mappe.php


namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;


/**
 * @ORM\Entity
 * @ORM\Table(name="mappe")
 */
class mappe
{

  /**
     * @ORM\Column(type="integer")
     * @ORM\Id
         */
    private $quota;


    /**
      * @ORM\Column(type="blob")
      */
    private $mappa_jpeg;

    /**
      * @ORM\Column(type="integer")
      */
    private $width;


    /**
      * @ORM\Column(type="integer")
      */
    private $versione;




    /**
     * Set quota
     *
     * @param integer $quota
     *
     * @return mappe
     */
    public function setQuota($quota)
    {
        $this->quota = $quota;

        return $this;
    }

    /**
     * Get quota
     *
     * @return integer
     */
    public function getQuota()
    {
        return $this->quota;
    }




    /**
     * Set width
     *
     * @param integer $width
     *
     * @return mappe
     */
    public function setWidth($width)
    {
        $this->width = $width;

        return $this;
    }

    /**
     * Get width
     *
     * @return integer
     */
    public function getWidth()
    {
        return $this->width;
    }

    /**
     * Set versione
     *
     * @param integer $versione
     *
     * @return mappe
     */
    public function setVersione($width)
    {
        $this->versione = $versione;

        return $this;
    }

    /**
     * Get versione
     *
     * @return integer
     */
    public function getVersione()
    {
        return $this->versione;
    }





    /**
     * Set mappaJpeg
     *
     * @param string $mappaJpeg
     *
     * @return mappe
     */
    public function setMappaJpeg($mappaJpeg)
    {
        $this->mappa_jpeg = $mappaJpeg;

        return $this;
    }

    /**
     * Get mappaJpeg
     *
     * @return blob
     */
    public function getMappaJpeg()
    {
        return $this->mappa_jpeg;
    }
}
