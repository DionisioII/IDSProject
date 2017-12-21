<?php
namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\ParameterBag;
use AppBundle\Entity\utente;
use AppBundle\Entity\archi;
use AppBundle\Entity\localizzazioni;
use Doctrine\ORM\Query;
use Doctrine\DBAL\Query\QueryBuilder;
use Doctrine\ORM\Query\ResultSetMapping;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Encoder\XmlEncoder;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\GetSetMethodNormalizer;
use SensioLabs\Security\Command\SecurityCheckerCommand;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\Common\Annotations\AnnotationReader;



class AppController extends Controller
{
  /**
  * @Route("/genus")
  */
  public function showAction()
  {
    return new Response ("Under the sea!");
  }


//METODO CHE RITORNA L IMMAGINE DELLA MAPPA
  /**
  * @Route("/aggiorna/{quota}")
  */
  public function aggiorna($quota)
  {
    $em = $this->getDoctrine()->getManager();
    $mappa = $em->getRepository('AppBundle:mappe')->find(array('quota' =>$quota));
if($mappa!=NULL){
    $b=$mappa->getMappaJpeg();



    $string = stream_get_contents($b);
    $response1 = new Response($string);

      $response1->headers->set('Content-Type', 'image/png');
    }else{
      $m='image not found';
      $response1 = new Response(json_encode(array('stato' => 'KO','risultato' =>$m)));
    }

    return ($response1);
  }



//METODO PER RICEVERE AGGIORNAMENTI DI NODI E ARCHI

  /**
  * @Route("/aggiornamenti")
  */
  public function aggiornamenti(Request $request)
  {
    //[{"id_map":145,"versione":4}, {"id_map":150,"versione":4}, {"id_map":155,"versione":3}]

    if ($request->getMethod() == 'POST') {

      $s=array();

      $data = json_decode($request->getContent(), true);


      foreach($data as $arr)
      {
        $quota=$arr['id_map'];
        $versione=$arr['versione'];
        $em = $this->getDoctrine()->getManager();
        $query = $em->getRepository('AppBundle:mappe')->find($quota);
        $versioneDB=$query->getVersione();

        if($versioneDB > $versione)
        {
          //  Devi aggiornare la versionE
          $s[]=array('versione'=>$versione,'quota'=>$quota);
          $mappeRestituire[]=array('id_map'=>$quota, 'versione'=>$versioneDB);
        }
      }
      foreach ($s as $q) {
        $quote[]=$q['quota'];
        $quoteDaRestituire[]['id_map']=$q['quota'];
      }


      if($s!=NULL){
        $newArchi=$this->jsonTable2('archi',$quote);
        $newNodi=$this->jsonTable('nodi',$quote);
        //var_dump($quote);

        $archi['archi']=$newArchi;
        $nodi['nodi']=$newNodi;
        //$mappe['mappe']=$quoteDaRestituire;
        $mappe['mappe']=$mappeRestituire;


    //->toArray(); //oppure usare questa funzione quando vuoi convertire l'oggetto in array

        $response1 = new Response(json_encode(array('stato' => 'OK','risultato' =>array($archi,$nodi,$mappe))));
      }
      else{

        $m='non ci sono aggiornamenti';
$response1 = new Response(json_encode(array('stato' => 'KO','risultato' =>$m)));
      }
    }

    return($response1);
  }


//METODO PER FARE LE QUERY PER GLI AGGIORNAMENTI di nodi

  public function jsonTable($tabella,$quote)
  {

    $condizione='a.id_map='.$quote[0];

    foreach($quote as $k=>$stringa)
    {
      if ($k < 1) continue;
      $condizione=$condizione.' OR '.'a.id_map='.$stringa;
    }

    $repository = $this->getDoctrine()->getRepository('AppBundle:'.$tabella);
    $qb=$repository->createQueryBuilder('a')
    ->where($condizione);
    // ->select('id', 'name')

    $query=$qb->getQuery();
    $result=$query->getArrayResult();
    return $result;
  }
  //METODO PER FARE LE QUERY selettiva PER GLI AGGIORNAMENTI di archi

    public function jsonTable2($tabella,$quote)
    {

      $condizione='a.id_map='.$quote[0];

      foreach($quote as $k=>$stringa)
      {
        if ($k < 1) continue;
        $condizione=$condizione.' OR '.'a.id_map='.$stringa;
      }

      $repository = $this->getDoctrine()->getRepository('AppBundle:'.$tabella);
      $qb=$repository->createQueryBuilder('a')
      ->select('a.id_map', 'a.nodo1','a.nodo2','a.K','a.area','a.lunghezza')
      ->where($condizione);
      // ->select('id', 'name')

      $query=$qb->getQuery();
      $result=$query->getArrayResult();
      return $result;
    }


//METODO PER RIMANERE LOGGATO GRAZIE AL TOKEN

  /**
  * @Route("/token" ,name="token")
  */
  public function tokenAction(Request $request)
  {
    //PROVA {"token":"dcf698e37c0f436007ca5d4ab1c5528bb65c4972"}
    if ($request->getMethod() == 'POST') {

      $data = json_decode($request->getContent(), true);
      $request->request->replace($data);

      $token = $request->request->get('token');


      $em = $this->getDoctrine()->getManager();
      $u = $em->getRepository('AppBundle:utente')->findBy(array('token' => $token));

      if($u != NULL){

        $response1 = new Response(json_encode(array('token' => 'ok')));
        $response1->headers->set('Content-Type', 'application/json');


      }else{
        $response1 = new Response(json_encode(array('errore' => 'token non valido')));
        $response1->headers->set('Content-Type', 'application/json');
      }

    }

    return $response1;
  }



//METODO PER EFFETTUARE IL LOGIN
  /**
  * @Route("/login" ,name="login")
  */
  public function loginAction(Request $request)
  {

    //PROVA {"email":"gb88","password":"pas"}
    if ($request->getMethod() == 'POST') {
      $data = json_decode($request->getContent(), true);
      $request->request->replace($data);

      $email= $request->request->get('email');
      $password = $request->request->get('password');

      $em = $this->getDoctrine()->getManager();
      $u = $em->getRepository('AppBundle:utente')->findBy(array('email' => $email, 'password' => $password));

      if($u != NULL){

        $utente=reset($u);

        $token=sha1(rand()."idstid20152016".$utente->getIdUtente());
        $utente->setToken($token);
        $em->flush();

        $response1 = new Response(json_encode(array('token' =>$token)));

      }else{

        $m='email o password errati';
        $response1 = new Response(json_encode(array('stato' => 'KO','risultato' =>$m)));
      }

    }else{
      $m='DEVI USARE UNA POST';
      $response1 = new Response(json_encode(array('stato' => 'KO','risultato' =>$m)));
    }


    return $response1;
  }



//METODO PER CALCOLARE K
  public function calcolaK($id_map,$nodo1,$nodo2)
  {
    $criteria1 = array('id_map' => $id_map,
    'nodo1' => $nodo1,
    'nodo2' => $nodo2);
    $em = $this->getDoctrine()->getManager();
    $arr = $em->getRepository('AppBundle:archi')->findBy($criteria1);
    $pv= reset($arr)->getPv();
    $pc= reset($arr)->getPc();
    $plos= reset($arr)->getPlos();
    $pi= reset($arr)->getPi();
    $v= reset($arr)->getV();
    $c= reset($arr)->getC();
    $los= reset($arr)->getLos();
    $i= reset($arr)->getI();


    $k=$pi*$i+$pc*$c+$plos*$los+$pv*$v;
    reset($arr)->setK($k);

    $em->persist(reset($arr));
    $em->flush();


    return($k);

  }






//METODO PER LOCALIZZARE UN UTENTE
  /**
  * @Route("/localizza")
  */
  public function localizzaUtente(Request $request){

    //{"id_map":"145","nodo1":"145A3","nodo2":"145EMA3","token":"9c86e190995ecb1dc747e7710b6559d1e7bc7900"}

    if ($request->getMethod() == 'POST') {

      $data = json_decode($request->getContent(), true);
      $request->request->replace($data);

      $id_map = $request->request->get('id_map');
      $nodo1 = $request->request->get('nodo1');
      $nodo2 = $request->request->get('nodo2');

      $token = $request->request->get('token');

      //PRIMO CONTROLLO CAMPI DELLA RICHIESTA NN DEVONO ESSE NULLI
      if($id_map!=NULL AND $nodo1!=NULL AND $nodo2!=NULL AND $token!=NULL){
        $criteria1 = array('id_map' => $id_map,
        'nodo1' => $nodo1,
        'nodo2' => $nodo2);

        $em = $this->getDoctrine()->getManager();

        $arco = $em->getRepository('AppBundle:archi')->findBy($criteria1);
        $utente1 = $em->getRepository('AppBundle:utente')->findBy(array('token' => $token));

        // SE ARCO E TOKEN UT TROVATI SUL DB
        if($arco!=NULL AND $utente1!=NULL){

          //CERCA ID UTENTE RICAVATO DAL TOKEN SU TABELLA LOCALIZZAZIONI
          $id_utente= reset ($utente1)->getIdUtente();
          $utenteLoc = $em->getRepository('AppBundle:localizzazioni')->find($id_utente);
          //SE UTENTE NON E PRESENTE IN LOCALIZZAZIONE LO CREA
          if($utenteLoc==NULL){
            //SE UTENTE NON SI E MAI LOCALIZZATO
            $localizza= new localizzazioni();
            $localizza->setIdMap($id_map);
            $localizza->setNodo1($nodo1);
            $localizza->setNodo2($nodo2);
            $localizza->setIdUtente($id_utente);
            $em->persist($localizza);
            $em->flush();
            $m= 'localizzazione effettuata';

          }else{
            //ALTRIMENTI
            // SE UTENTE GIA LOCALIZZATO , UPDATE DELLA POSIZIONE NEL DB
            $utenteLoc->setIdMap($id_map);
            $utenteLoc->setNodo1($nodo1);
            $utenteLoc->setNodo2($nodo2);

            $em->flush();
            $m= 'localizzazione aggiornata';
          }

          $los=$this->calcolaLos($id_map,$nodo1,$nodo2);
          $k=$this->calcolaK($id_map,$nodo1,$nodo2);
          $tuttiK=$this->getAllK();

          $response1= new Response(json_encode(array('stato' => 'OK','risultato' =>$tuttiK, 'descrizione' =>$m)));

        }else{
          $m= 'arco o utente non presenti';
          $response1 = new Response(json_encode(array('stato' => 'KO','risultato' =>$m)));
        }

      }else{
        $m= 'Parametri richiesta nulli-sbagliati';
        $response1 = new Response(json_encode(array('stato' => 'KO','risultato' =>$m)));
      }

    }else{
      $m='DEVI USARE UNA POST';
      $response1 = new Response(json_encode(array('stato' => 'KO','risultato' =>$m)));
    }




    return $response1;
  }


  public function calcolaLos($id_map,$nodo1,$nodo2)

  {
    $criteria1 = array('id_map' => $id_map,
    'nodo1' => $nodo1,
    'nodo2' => $nodo2);

    $em = $this->getDoctrine()->getManager();
    $arco = $em->getRepository('AppBundle:archi')->findBy($criteria1);
    $localizzazioni = $em->getRepository('AppBundle:localizzazioni')->findBy($criteria1);
    $persone=count($localizzazioni);

    $area= reset($arco)->getArea();
    $rapporto= $area/$persone;
    $Los;

    switch ($rapporto) {
      case ($rapporto > 3.7):
      $Los=0;
      break;
      case ($rapporto >= 2.2 AND $rapporto<=3.7):
      $Los=0.33;
      break;
      case ($rapporto >=1.4 AND $rapporto<2.2):
      $Los=0.67;
      break;
      case ($rapporto >= 0.75 AND $rapporto<1.4):
      $Los=1;
      break;
      case ($rapporto<0.75):
      $Los=3;
      break;


    }    reset ($arco)->setLos($Los);


    $em->persist(reset ($arco));
    $em->flush();
    return $Los;
  }


//METODO PER REGISTRARE UN UTENTE E CONTROLLO DATI
  /**
  * @Route("/registra")
  */
  public function registraAction(Request $request)
  {
    if ($request->getMethod() == 'POST') {
      //{"nome":"Mario","cognome":"Rossi","password":"password","email":"mr86@gmail.com"}

      $data = json_decode($request->getContent(), true);
      $request->request->replace($data);
      $nome = $request->request->get('nome');
      $cognome = $request->request->get('cognome');
      $email = $request->request->get('email');
      $password = $request->request->get('password');

      $utente = new utente();

      $utente->setNome($nome);
      $utente->setCognome($cognome);
      $utente->setPassword($password);
      $utente->setEmail($email);

      $em = $this->getDoctrine()->getEntityManager();

      $validator = $this->get('validator');
      $errors = $validator->validate($utente);

      if (count($errors) > 0) {
        $encoders = array(new XmlEncoder(), new JsonEncoder());
        $normalizers = array(new GetSetMethodNormalizer());
        $serializer = new Serializer($normalizers, $encoders);

        foreach ($errors as $error)
        {
          $campo = $error->getPropertyPath();
          $messaggio = $error->getMessage();
          $definitivo[][$campo]=$messaggio;
        }

        $response1 = new Response(json_encode(array('stato' => 'KO','risultato' =>$definitivo)));
        $response1->headers->set('Content-Type', 'application/json');
      }else{

        $criteria2 = array('email' => $email);
        $utenteEmail = $this->getDoctrine()->getRepository('AppBundle:utente')->findBy($criteria2);

        if ($utenteEmail) {
          $r= "email non disponibile";
          $response=(array(array('email' => $r)));
          $response1 = new Response(json_encode(array('stato' => 'KO','risultato' =>$response)));
          $response1->headers->set('Content-Type', 'application/json');
        }else{
          //email disponibile

          $token=sha1(rand()."idstid20152016".$utente->getIdUtente());
          $utente->setToken($token);

          $em->persist($utente);
          $em->flush();
          $tokenJ=(array(array('token' => $token)));

          $response1 = new Response(json_encode(array('stato' => 'OK','risultato' =>$tokenJ)));
                    $response1->headers->set('Content-Type', 'application/json');
        }
      }
      return ($response1);

    }
    $response1 = new Response(json_encode(array('errore' => 'deve essere post')));
    $response1->headers->set('Content-Type', 'application/json');
    return ($response1);

  }


//METODO PER SIMULARE LA PRESENZA DI UN EMERGENZA
    /**
    * @Route("/getStato")
    */
    public function getEmergenza(Request $request)
    {
      if ($request->getMethod() == 'GET') {
        $em = $this->getDoctrine()->getManager();
        $emergenza = $em->getRepository('AppBundle:emergenze')->find('3');
        $stato=$emergenza->getStato();

if($stato==1){
  $m='emergenza';
  $response1 = new Response(json_encode(array('stato' => 'KO','risultato' =>$m)));

}else{
  $m='non emergenza';
  $response1 = new Response(json_encode(array('stato' => 'OK','risultato' =>$m)));


}

      }else{
        $m='deve essere una get';
        $response1 = new Response(json_encode(array('stato' => 'KO','risultato' =>$m)));
      }

      return ($response1);
    }


  //METODO PER RESTITUIRE TUTTI I K DAL DB METODO GET

  /**
  * @Route("/getK")
  */
  public function restituisciK(Request $request)
  {
    if ($request->getMethod() == 'GET') {
      $repository = $this->getDoctrine()->getRepository('AppBundle:archi');

      $qb=$repository->createQueryBuilder('a');
      $qb->select('a.id_map','a.nodo1','a.nodo2','a.K');
      $query=$qb->getQuery();
      $result=$query->getResult();

      $response1 = new Response(json_encode(array('stato' => 'OK','risultato' =>$result)));
    }else{
      $m='deve essere una get';
      $response1 = new Response(json_encode(array('stato' => 'KO','risultato' =>$m)));
    }

    return ($response1);
  }

  //METODO PER RESTITUIRE TUTTI I K DAL DB
  public function getAllK()
  {

    $repository = $this->getDoctrine()->getRepository('AppBundle:archi');

    $qb=$repository->createQueryBuilder('a');
    $qb->select('a.id_map','a.nodo1','a.nodo2','a.K');
    $query=$qb->getQuery();
    $result=$query->getResult();

    return ($result);

  }





}
