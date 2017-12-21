<?php

use Symfony\Component\Routing\RouteCollection;
use Symfony\Component\Routing\Route;

$collection = new RouteCollection();
$collection->add('login', new Route('/login', array(
    '_controller' => 'AppBundle:Security:login',
)));

return $collection;










 ?>
