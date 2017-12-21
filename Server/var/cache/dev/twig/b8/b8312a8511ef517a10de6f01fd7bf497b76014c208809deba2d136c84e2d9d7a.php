<?php

/* @Twig/Exception/exception_full.html.twig */
class __TwigTemplate_7042b2e7ec08cac51e22c0fb98c4020ad45830dd0c5210ce6e75b55aa2d1a574 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        // line 1
        $this->parent = $this->loadTemplate("@Twig/layout.html.twig", "@Twig/Exception/exception_full.html.twig", 1);
        $this->blocks = array(
            'head' => array($this, 'block_head'),
            'title' => array($this, 'block_title'),
            'body' => array($this, 'block_body'),
        );
    }

    protected function doGetParent(array $context)
    {
        return "@Twig/layout.html.twig";
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        $__internal_a9f29de771a4e1368b43476980f16dfd11f050f8e2cbba2b60d37fa2d50928e2 = $this->env->getExtension("native_profiler");
        $__internal_a9f29de771a4e1368b43476980f16dfd11f050f8e2cbba2b60d37fa2d50928e2->enter($__internal_a9f29de771a4e1368b43476980f16dfd11f050f8e2cbba2b60d37fa2d50928e2_prof = new Twig_Profiler_Profile($this->getTemplateName(), "template", "@Twig/Exception/exception_full.html.twig"));

        $this->parent->display($context, array_merge($this->blocks, $blocks));
        
        $__internal_a9f29de771a4e1368b43476980f16dfd11f050f8e2cbba2b60d37fa2d50928e2->leave($__internal_a9f29de771a4e1368b43476980f16dfd11f050f8e2cbba2b60d37fa2d50928e2_prof);

    }

    // line 3
    public function block_head($context, array $blocks = array())
    {
        $__internal_a0702f7c5437622859c8016402b2c77028f0243ae3d16123148dedb959dea78d = $this->env->getExtension("native_profiler");
        $__internal_a0702f7c5437622859c8016402b2c77028f0243ae3d16123148dedb959dea78d->enter($__internal_a0702f7c5437622859c8016402b2c77028f0243ae3d16123148dedb959dea78d_prof = new Twig_Profiler_Profile($this->getTemplateName(), "block", "head"));

        // line 4
        echo "    <link href=\"";
        echo twig_escape_filter($this->env, $this->env->getExtension('request')->generateAbsoluteUrl($this->env->getExtension('asset')->getAssetUrl("bundles/framework/css/exception.css")), "html", null, true);
        echo "\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" />
";
        
        $__internal_a0702f7c5437622859c8016402b2c77028f0243ae3d16123148dedb959dea78d->leave($__internal_a0702f7c5437622859c8016402b2c77028f0243ae3d16123148dedb959dea78d_prof);

    }

    // line 7
    public function block_title($context, array $blocks = array())
    {
        $__internal_c1d98560b1903e624138fbd2ce44477d688f8b164c2aadde051a535cdbfd753e = $this->env->getExtension("native_profiler");
        $__internal_c1d98560b1903e624138fbd2ce44477d688f8b164c2aadde051a535cdbfd753e->enter($__internal_c1d98560b1903e624138fbd2ce44477d688f8b164c2aadde051a535cdbfd753e_prof = new Twig_Profiler_Profile($this->getTemplateName(), "block", "title"));

        // line 8
        echo "    ";
        echo twig_escape_filter($this->env, $this->getAttribute((isset($context["exception"]) ? $context["exception"] : $this->getContext($context, "exception")), "message", array()), "html", null, true);
        echo " (";
        echo twig_escape_filter($this->env, (isset($context["status_code"]) ? $context["status_code"] : $this->getContext($context, "status_code")), "html", null, true);
        echo " ";
        echo twig_escape_filter($this->env, (isset($context["status_text"]) ? $context["status_text"] : $this->getContext($context, "status_text")), "html", null, true);
        echo ")
";
        
        $__internal_c1d98560b1903e624138fbd2ce44477d688f8b164c2aadde051a535cdbfd753e->leave($__internal_c1d98560b1903e624138fbd2ce44477d688f8b164c2aadde051a535cdbfd753e_prof);

    }

    // line 11
    public function block_body($context, array $blocks = array())
    {
        $__internal_b4bd95770eb6f8de3ec01709c6298e8fc4c54d8bb8890dd32f6dba569ddaaa16 = $this->env->getExtension("native_profiler");
        $__internal_b4bd95770eb6f8de3ec01709c6298e8fc4c54d8bb8890dd32f6dba569ddaaa16->enter($__internal_b4bd95770eb6f8de3ec01709c6298e8fc4c54d8bb8890dd32f6dba569ddaaa16_prof = new Twig_Profiler_Profile($this->getTemplateName(), "block", "body"));

        // line 12
        echo "    ";
        $this->loadTemplate("@Twig/Exception/exception.html.twig", "@Twig/Exception/exception_full.html.twig", 12)->display($context);
        
        $__internal_b4bd95770eb6f8de3ec01709c6298e8fc4c54d8bb8890dd32f6dba569ddaaa16->leave($__internal_b4bd95770eb6f8de3ec01709c6298e8fc4c54d8bb8890dd32f6dba569ddaaa16_prof);

    }

    public function getTemplateName()
    {
        return "@Twig/Exception/exception_full.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  78 => 12,  72 => 11,  58 => 8,  52 => 7,  42 => 4,  36 => 3,  11 => 1,);
    }
}
/* {% extends '@Twig/layout.html.twig' %}*/
/* */
/* {% block head %}*/
/*     <link href="{{ absolute_url(asset('bundles/framework/css/exception.css')) }}" rel="stylesheet" type="text/css" media="all" />*/
/* {% endblock %}*/
/* */
/* {% block title %}*/
/*     {{ exception.message }} ({{ status_code }} {{ status_text }})*/
/* {% endblock %}*/
/* */
/* {% block body %}*/
/*     {% include '@Twig/Exception/exception.html.twig' %}*/
/* {% endblock %}*/
/* */
