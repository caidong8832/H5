jQuery(document).ready(function($) {
    var is_bouncy_nav_animating = false;
   
    $('.cd-bouncy-nav-trigger').on('click', function (e) {
       // e
        triggerBouncyNav(true);
        return false;
    });
   
    $('.cd-bouncy-nav-modal .cd-close').on('click', function() {
        triggerBouncyNav(false);
    });
    $('.cd-bouncy-nav-modal').on('click', function(event) {
        if ($(event.target).is('.cd-bouncy-nav-modal')) {
            triggerBouncyNav(false);
        }
    });
    function triggerBouncyNav($bool) {
        
        if (!is_bouncy_nav_animating) {
            is_bouncy_nav_animating = true;
          
            $('.cd-bouncy-nav-modal').toggleClass('fade-in', $bool).toggleClass('fade-out', !$bool).find('li:last-child').one('webkitAnimationEnd oanimationend msAnimationEnd animationend', function() {
                $('.cd-bouncy-nav-modal').toggleClass('is-visible', $bool);
                if (!$bool)
                    $('.cd-bouncy-nav-modal').removeClass('fade-out');
                is_bouncy_nav_animating = false;
            });
            
            if ($('.cd-bouncy-nav-trigger').parents('.no-csstransitions').length > 0) {
                $('.cd-bouncy-nav-modal').toggleClass('is-visible', $bool);
                is_bouncy_nav_animating = false;
            }
        }
    }
});