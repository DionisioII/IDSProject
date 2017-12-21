<?php

/* @WebProfiler/Collector/router.html.twig */
class __TwigTemplate_a12af58b18fc090df01fdbfedd6db46f23b5fef27f8c882ff0ce47e2ffd2c281 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        // line 1
        $this->parent = $this->loadTemplate("@WebProfiler/Profiler/layout.html.twig", "@WebProfiler/Collector/router.html.twig", 1);
        $this->blocks = array(
            'toolbar' => array($this, 'block_toolbar'),
            'menu' => array($this, 'block_menu'),
            'panel' => array($this, 'block_panel'),
        );
    }

    protected function doGetParent(array $context)
    {
        return "@WebProfiler/Profiler/layout.html.twig";
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        $__internal_f50b66e683870548eb327498c994feb2ca94a8a176d97f3b7a094ac542c23721 = $this->env->getExtension("native_profiler");
        $__internal_f50b66e683870548eb327498c994feb2ca94a8a176d97f3b7a094ac542c23721->enter($__internal_f50b66e683870548eb327498c994feb2ca94a8a176d97f3b7a094ac542c23721_prof = new Twig_Profiler_Profile($this->getTemplateName(), "template", "@WebProfiler/Collector/router.html.twig"));

        $this->parent->display($context, array_merge($this->blocks, $blocks));
        
        $__internal_f50b66e683870548eb327498c994feb2ca94a8a176d97f3b7a094ac542c23721->leave($__internal_f50b66e683870548eb327498c994feb2ca94a8a176d97f3b7a094ac542c23721_prof);

    }

    // line 3
    public function block_toolbar($context, array $blocks = array())
    {
        $__internal_6e1ea29a7d119e6547e2f18f0291f74176c1b8920d3984a62f653c16c56d57bb = $this->env->getExtension("native_profiler");
        $__internal_6e1ea29a7d119e6547e2f18f0291f74176c1b8920d3984a62f653c16c56d57bb->enter($__internal_6e1ea29a7d119e6547e2f18f0291f74176c1b8920d3984a62f653c16c56d57bb_prof = new Twig_Profiler_Profile($this->getTemplateName(), "block", "toolbar"));

        
        $__internal_6e1ea29a7d119e6547e2f18f0291f74176c1b8920d3984a62f653c16c56d57bb->leave($__internal_6e1ea29a7d119e6547e2f18f0291f74176c1b8920d3984a62f653c16c56d57bb_prof);

    }

    // line 5
    public function block_menu($context, array $blocks = array())
    {
        $__internal_f2243aae224648f7f27e61992e55a40caf858e50a7c08427d3a006a0f9a5b7ba = $this->env->getExtension("native_profiler");
        $__internal_f2243aae224648f7f27e61992e55a40caf858e50a7c08427d3a006a0f9a5b7ba->enter($__internal_f2243aae224648f7f27e61992e55a40caf858e50a7c08427d3a006a0f9a5b7ba_prof = new Twig_Profiler_Profile($this->getTemplateName(), "block", "menu"));

        // line 6
        echo "<span class=\"label\">
    <span class=\"icon\">";
        // line 7
        echo twig_include($this->env, $context, "@WebProfiler/Icon/router.svg");
        echo "</span>
    <strong>Routing</strong>
</span>
";
        
        $__internal_f2243aae224648f7f27e61992e55a40caf858e50a7c08427d3a006a0f9a5b7ba->leave($__internal_f2243aae224648f7f27e61992e55a40caf858e50a7c08427d3a006a0f9a5b7ba_prof);

    }

    // line 12
    public function block_panel($context, array $blocks = array())
    {
        $__internal_f41b1f5e213ea3508aae6c81d35d254f6d04787a277055337a18fbd8fa4547da = $this->env->getExtension("native_profiler");
        $__internal_f41b1f5e213ea3508aae6c81d35d254f6d04787a277055337a18fbd8fa4547da->enter($__internal_f41b1f5e213ea3508aae6c81d35d254f6d04787a277055337a18fbd8fa4547da_prof = new Twig_Profiler_Profile($this->getTemplateName(), "block", "panel"));

        // line 13
        echo "    ";
        echo $this->env->getExtension('http_kernel')->renderFragment($this->env->getExtension('routing')->getPath("_profiler_router", array("token" => (isset($context["token"]) ? $context["token"] : $this->getContext($context, "token")))));
        echo "
";
        
        $__internal_f41b1f5e213ea3508aae6c81d35d254f6d04787a277055337a18fbd8fa4547da->leave($__internal_f41b1f5e213ea3508aae6c81d35d254f6d04787a277055337a18fbd8fa4547da_prof);

    }

    public function getTemplateName()
    {
        return "@WebProfiler/Collector/router.html.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  73 => 13,  67 => 12,  56 => 7,  53 => 6,  47 => 5,  36 => 3,  11 => 1,);
    }
}
/* {% extends '@WebProfiler/Profiler/layout.html.twig' %}*/
/* */
/* {% block toolbar %}{% endblock %}*/
/* */
/* {% block menu %}*/
/* <span class="label">*/
/*     <span class="icon">{{ include('@WebProfiler/Icon/router.svg') }}</span>*/
/*     <strong>Routing</strong>*/
/* </span>*/
/* {% endblock %}*/
/* */
/* {% block panel %}*/
/*     {{ render(path('_profiler_router', { token: token })) }}*/
/* {% endblock %}*/
/* */
