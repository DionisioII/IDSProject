<?php

/* @WebProfiler/Profiler/header.html.twig */
class __TwigTemplate_552cdc1adf1508b77e60cad0945623b6eb7460bc3af05ef7a54a16f9371ef0d4 extends Twig_Template
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
        $__internal_9b239f8d4ec0e14eb46f26ae5a916c7738875b627019f5dcb142cbf200d6d6f2 = $this->env->getExtension("native_profiler");
        $__internal_9b239f8d4ec0e14eb46f26ae5a916c7738875b627019f5dcb142cbf200d6d6f2->enter($__internal_9b239f8d4ec0e14eb46f26ae5a916c7738875b627019f5dcb142cbf200d6d6f2_prof = new Twig_Profiler_Profile($this->getTemplateName(), "template", "@WebProfiler/Profiler/header.html.twig"));

        // line 1
        echo "<div id=\"header\">
    <div class=\"container\">
        <h1>";
        // line 3
        echo twig_include($this->env, $context, "@WebProfiler/Icon/symfony.svg");
        echo " Symfony <span>Profiler</span></h1>

        <div class=\"search\">
            <form method=\"get\" action=\"https://symfony.com/search\" target=\"_blank\">
                <div class=\"form-row\">
                    <input name=\"q\" id=\"search-id\" type=\"search\" placeholder=\"search on symfony.com\">
                    <button type=\"submit\" class=\"btn\">Search</button>
                </div>
           </form>
        </div>
    </div>
</div>
";
        
        $__internal_9b239f8d4ec0e14eb46f26ae5a916c7738875b627019f5dcb142cbf200d6d6f2->leave($__internal_9b239f8d4ec0e14eb46f26ae5a916c7738875b627019f5dcb142cbf200d6d6f2_prof);

    }

    public function getTemplateName()
    {
        return "@WebProfiler/Profiler/header.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  26 => 3,  22 => 1,);
    }
}
/* <div id="header">*/
/*     <div class="container">*/
/*         <h1>{{ include('@WebProfiler/Icon/symfony.svg') }} Symfony <span>Profiler</span></h1>*/
/* */
/*         <div class="search">*/
/*             <form method="get" action="https://symfony.com/search" target="_blank">*/
/*                 <div class="form-row">*/
/*                     <input name="q" id="search-id" type="search" placeholder="search on symfony.com">*/
/*                     <button type="submit" class="btn">Search</button>*/
/*                 </div>*/
/*            </form>*/
/*         </div>*/
/*     </div>*/
/* </div>*/
/* */
