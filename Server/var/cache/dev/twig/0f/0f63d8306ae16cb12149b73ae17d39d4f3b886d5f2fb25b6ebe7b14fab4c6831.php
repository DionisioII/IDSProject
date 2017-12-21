<?php

/* security/login.html.php */
class __TwigTemplate_5d18bc6c6a04feb0e534d793a360f0bab91e6685d172a3d6befa23df617a49ba extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = array(
        );
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        $__internal_1ce9cea9238ccc9736706a05ec4530c9b689f6e31683d36e6b23a69b60da41c5 = $this->env->getExtension("native_profiler");
        $__internal_1ce9cea9238ccc9736706a05ec4530c9b689f6e31683d36e6b23a69b60da41c5->enter($__internal_1ce9cea9238ccc9736706a05ec4530c9b689f6e31683d36e6b23a69b60da41c5_prof = new Twig_Profiler_Profile($this->getTemplateName(), "template", "security/login.html.php"));

        // line 1
        echo "<!-- src/AppBundle/Resources/views/Security/login.html.php -->
<?php
if (\$error)
echo (\$error->getMessage());
<?php endif?>



<form action=\"<?php echo \$view['router']->path('login') ?>\" method=\"POST\">
    <label for=\"username\">Username:</label>
    <input type=\"text\" id=\"username\" name=\"_username\" value=\"<?php echo \$last_username ?>\" />

    <label for=\"password\">Password:</label>
    <input type=\"password\" id=\"password\" name=\"_password\" />

    <!--
        If you want to control the URL the user
        is redirected to on success (more details below)
        <input type=\"hidden\" name=\"_target_path\" value=\"/account\" />
    -->

    <button type=\"submit\">login</button>

</form>
";
        
        $__internal_1ce9cea9238ccc9736706a05ec4530c9b689f6e31683d36e6b23a69b60da41c5->leave($__internal_1ce9cea9238ccc9736706a05ec4530c9b689f6e31683d36e6b23a69b60da41c5_prof);

    }

    public function getTemplateName()
    {
        return "security/login.html.php";
    }

    public function getDebugInfo()
    {
        return array (  22 => 1,);
    }
}
/* <!-- src/AppBundle/Resources/views/Security/login.html.php -->*/
/* <?php*/
/* if ($error)*/
/* echo ($error->getMessage());*/
/* <?php endif?>*/
/* */
/* */
/* */
/* <form action="<?php echo $view['router']->path('login') ?>" method="POST">*/
/*     <label for="username">Username:</label>*/
/*     <input type="text" id="username" name="_username" value="<?php echo $last_username ?>" />*/
/* */
/*     <label for="password">Password:</label>*/
/*     <input type="password" id="password" name="_password" />*/
/* */
/*     <!--*/
/*         If you want to control the URL the user*/
/*         is redirected to on success (more details below)*/
/*         <input type="hidden" name="_target_path" value="/account" />*/
/*     -->*/
/* */
/*     <button type="submit">login</button>*/
/* */
/* </form>*/
/* */
