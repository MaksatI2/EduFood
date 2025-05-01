<#import "/spring.ftl" as spring>

<#macro page title="" styles="" activeNav="">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
    <link rel="icon" href="/static/images/EduFood2.png" type="image/x-icon">
    <#if styles?has_content>
        <link rel="stylesheet" href="${styles}">
    </#if>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
    <div class="container">
        <a class="navbar-brand" href="/">
            <i class="fas fa-utensils me-2"></i>EduFood
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a href="/" class="nav-link <#if activeNav == "home">active</#if>">Главная</a>
                </li>
                <li class="nav-item">
                    <a href="/restaurants" class="nav-link <#if activeNav == "restaurants">active</#if>">Рестораны</a>
                </li>
                <li class="nav-item">
                    <a href="/dishes" class="nav-link <#if activeNav == "dishes">active</#if>">Блюда</a>
                </li>
                <li class="nav-item">
                    <a href="/orders/cart" class="nav-link <#if activeNav == "basket">active</#if>">Корзина<i class="fas fa-shopping-cart ms-1"></i></a>
                </li>
            </ul>

            <div class="d-flex align-items-center">
                <#if Session.SPRING_SECURITY_CONTEXT??>
                    <a href="/profile" class="btn btn-outline-primary me-2 <#if activeNav == "profile">active</#if>">
                        <i class="fas fa-user-circle"></i>
                    </a>
                    <#if activeNav == "profile" && _csrf??>
                        <form action="/logout" method="post" class="d-inline">
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
                            <button type="submit" class="btn btn-danger">Выйти</button>
                        </form>
                    </#if>
                <#else>
                    <a href="/auth/login" class="btn btn-outline-primary me-2 <#if activeNav == "login">active</#if>">Войти</a>
                </#if>
            </div>
        </div>
    </div>
</nav>


<main class="main">
    <div class="container">
        <#nested>
    </div>
</main>

<footer>
    <div class="container">
        <div class="row">
            <div class="col-md-4 mb-4">
                <h4><i class="fas fa-utensils me-2"></i>EduFood</h4>
                <p class="mt-3">Доставка вкусной еды в любое время дня и ночи. Более 100 ресторанов-партнеров.</p>
                <div class="mt-4">
                    <a href="#" class="social-icon"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" class="social-icon"><i class="fab fa-instagram"></i></a>
                    <a href="#" class="social-icon"><i class="fab fa-telegram"></i></a>
                    <a href="#" class="social-icon"><i class="fab fa-github"></i></a>
                </div>
            </div>
            <div class="col-md-4 mb-4">
                <h5>Контакты</h5>
                <ul class="list-unstyled">
                    <li class="mb-2"><i class="fas fa-map-marker-alt me-2"></i> г. Бишкек, ул. Малдыбаева 7/1</li>
                    <li class="mb-2"><i class="fas fa-phone me-2"></i> +996 700270984</li>
                    <li class="mb-2"><i class="fas fa-envelope me-2"></i> zer0icemax@gmail.com</li>
                </ul>
            </div>
        </div>
        <hr class="mt-4 bg-light">
        <div class="text-center pt-3">
            <p class="mb-0">© 2025 EduFood. Все права защищены.</p>
        </div>
    </div>
</footer>

</body>
</html>
</#macro>
