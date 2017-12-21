<?php

// app/config/security.php
$container->loadFromExtension('security', array(
    'firewalls' => array(
        'main' => array(
            'anonymous'  => null,
            'form_login' => array(
                'login_path' => 'login',
                'check_path' => 'login',
            ),
        ),
    ),
));
