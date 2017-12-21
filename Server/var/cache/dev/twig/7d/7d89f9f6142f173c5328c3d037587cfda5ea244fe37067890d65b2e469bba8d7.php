<?php

/* base.html.twig */
class __TwigTemplate_c9cd8c1abacffa9309a1577432e1657bb1f32da862e8d286e40e0a76edd2161d extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = array(
            'title' => array($this, 'block_title'),
            'stylesheets' => array($this, 'block_stylesheets'),
            'body' => array($this, 'block_body'),
            'javascripts' => array($this, 'block_javascripts'),
        );
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        $__internal_27180a6e0f1f5c0f22633daad2046fab2a4d295252c8428867570611f418ecff = $this->env->getExtension("native_profiler");
        $__internal_27180a6e0f1f5c0f22633daad2046fab2a4d295252c8428867570611f418ecff->enter($__internal_27180a6e0f1f5c0f22633daad2046fab2a4d295252c8428867570611f418ecff_prof = new Twig_Profiler_Profile($this->getTemplateName(), "template", "base.html.twig"));

        // line 1
        echo "<!DOCTYPE html>
<html>
    <head>
        <meta charset=\"UTF-8\" />
        <title>";
        // line 5
        $this->displayBlock('title', $context, $blocks);
        echo "</title>
        ";
        // line 6
        $this->displayBlock('stylesheets', $context, $blocks);
        // line 7
        echo "        <link rel=\"icon\" type=\"image/x-icon\" href=\"";
        echo twig_escape_filter($this->env, $this->env->getExtension('asset')->getAssetUrl("favicon.ico"), "html", null, true);
        echo "\" />
    </head>
    <body>
        ";
        // line 10
        $this->displayBlock('body', $context, $blocks);
        // line 11
        echo "        ";
        $this->displayBlock('javascripts', $context, $blocks);
        // line 12
        echo "    </body>
</html>
";
        
        $__internal_27180a6e0f1f5c0f22633daad2046fab2a4d295252c8428867570611f418ecff->leave($__internal_27180a6e0f1f5c0f22633daad2046fab2a4d295252c8428867570611f418ecff_prof);

    }

    // line 5
    public function block_title($context, array $blocks = array())
    {
        $__internal_efd9441382ba96fc34024344b06a2b24fa9eaceccc02691dec0122b5fcdb2804 = $this->env->getExtension("native_profiler");
        $__internal_efd9441382ba96fc34024344b06a2b24fa9eaceccc02691dec0122b5fcdb2804->enter($__internal_efd9441382ba96fc34024344b06a2b24fa9eaceccc02691dec0122b5fcdb2804_prof = new Twig_Profiler_Profile($this->getTemplateName(), "block", "title"));

        echo "Welcome!";
        
        $__internal_efd9441382ba96fc34024344b06a2b24fa9eaceccc02691dec0122b5fcdb2804->leave($__internal_efd9441382ba96fc34024344b06a2b24fa9eaceccc02691dec0122b5fcdb2804_prof);

    }

    // line 6
    public function block_stylesheets($context, array $blocks = array())
    {
        $__internal_88b4ece6ef29b0d45044661a546c86e05d272a12540955a4424e1494d046b9f8 = $this->env->getExtension("native_profiler");
        $__internal_88b4ece6ef29b0d45044661a546c86e05d272a12540955a4424e1494d046b9f8->enter($__internal_88b4ece6ef29b0d45044661a546c86e05d272a12540955a4424e1494d046b9f8_prof = new Twig_Profiler_Profile($this->getTemplateName(), "block", "stylesheets"));

        
        $__internal_88b4ece6ef29b0d45044661a546c86e05d272a12540955a4424e1494d046b9f8->leave($__internal_88b4ece6ef29b0d45044661a546c86e05d272a12540955a4424e1494d046b9f8_prof);

    }

    // line 10
    public function block_body($context, array $blocks = array())
    {
        $__internal_e695b3f0b9a0384e21a014d522d8b442780da72b860a8f1b44ae09cb6a95862e = $this->env->getExtension("native_profiler");
        $__internal_e695b3f0b9a0384e21a014d522d8b442780da72b860a8f1b44ae09cb6a95862e->enter($__internal_e695b3f0b9a0384e21a014d522d8b442780da72b860a8f1b44ae09cb6a95862e_prof = new Twig_Profiler_Profile($this->getTemplateName(), "block", "body"));

        
        $__internal_e695b3f0b9a0384e21a014d522d8b442780da72b860a8f1b44ae09cb6a95862e->leave($__internal_e695b3f0b9a0384e21a014d522d8b442780da72b860a8f1b44ae09cb6a95862e_prof);

    }

    // line 11
    public function block_javascripts($context, array $blocks = array())
    {
        $__internal_b1bede500fa3725387ae8053de333bfb972bbb23a90eb9e7668b0333ff64f5ce = $this->env->getExtension("native_profiler");
        $__internal_b1bede500fa3725387ae8053de333bfb972bbb23a90eb9e7668b0333ff64f5ce->enter($__internal_b1bede500fa3725387ae8053de333bfb972bbb23a90eb9e7668b0333ff64f5ce_prof = new Twig_Profiler_Profile($this->getTemplateName(), "block", "javascripts"));

        
        $__internal_b1bede500fa3725387ae8053de333bfb972bbb23a90eb9e7668b0333ff64f5ce->leave($__internal_b1bede500fa3725387ae8053de333bfb972bbb23a90eb9e7668b0333ff64f5ce_prof);

    }

    public function getTemplateName()
    {
        return "base.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  93 => 11,  82 => 10,  71 => 6,  59 => 5,  50 => 12,  47 => 11,  45 => 10,  38 => 7,  36 => 6,  32 => 5,  26 => 1,);
    }
}
/* <!DOCTYPE html>*/
/* <html>*/
/*     <head>*/
/*         <meta charset="UTF-8" />*/
/*         <title>{% block title %}Welcome!{% endblock %}</title>*/
/*         {% block stylesheets %}{% endblock %}*/
/*         <link rel="icon" type="image/x-icon" href="{{ asset('favicon.ico') }}" />*/
/*     </head>*/
/*     <body>*/
/*         {% block body %}{% endblock %}*/
/*         {% block javascripts %}{% endblock %}*/
/*     </body>*/
/* </html>*/
/* */
