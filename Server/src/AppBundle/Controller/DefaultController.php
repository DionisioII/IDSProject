<?php

namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use AppBundle\Entity\utente;

class DefaultController extends Controller
{
    /**
     * @Route("/", name="homepage")
     */
    public function indexAction(Request $request)
    {
        // replace this example code with whatever you need
        return $this->render('default/index.html.twig', [
            'base_dir' => realpath($this->getParameter('kernel.root_dir').'/..'),
        ]);
    }


      /**
       * @Route("/prova")
       */
      public function createAction()
      {
          $utente = new utente();
          //$utente->setName('Keyboard');
          $utente->setNome('Lara');
          $utente->setCognome('Trussi');
          $utente->setUsername('lr91');
          $utente->setPassword('lr991');
          $utente->setEmail('lr@l.it');


              $em = $this->getDoctrine()->getEntityManager();

          // tells Doctrine you want to (eventually) save the Product (no queries yet)
          $em->persist($utente);

          // actually executes the queries (i.e. the INSERT query)
          $em->flush();

          return new Response('Utente salvato con id '.$utente->getIdUtente());
      }




}
