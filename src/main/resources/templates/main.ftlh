<#import "/spring.ftl" as spring>
<#import "layout.ftlh" as layout>

<@layout.page title="EduFood - Доставка вкусной еды" styles="/static/css/main.css" activeNav="home">
    <section class="hero-section text-center">
        <div class="container">
            <h1 class="display-4 fw-bold mb-4">Доставка вкусной еды в вашем городе</h1>
            <p class="lead mb-5">Более 100 ресторанов с быстрой доставкой до двери</p>
            <div class="row justify-content-center">
                <div class="col-md-8">

                        <form method="get" action="/" class="input-group mb-3">

                            <input type="text" name="search" class="form-control form-control-lg" placeholder="Введите названия ресторана...">
                            <button class="btn btn-primary btn-lg" type="submit">Найти рестораны</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <#if restaurants?? && restaurants?size gt 0>
        <section class="container mb-5">
            <h2 class="mb-4 text-center">Результаты поиска ресторанов</h2>
            <div class="row">
                <#list restaurants as restaurant>
                    <div class="col-lg-4 col-md-6">
                        <div class="restaurant-card card h-100">
                            <img src="/static/restaurants/${restaurant.photoUrl}"
                                 class="card-img-top"
                                 style="height: 250px; object-fit: cover;"
                                 alt="${restaurant.name}">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${restaurant.name}</h5>
                                <p class="card-text text-muted">
                                    <#if restaurant.dishes?size gt 0>
                                        ${restaurant.dishes?size} блюд
                                    <#else>
                                        Меню недоступно
                                    </#if>
                                </p>
                                <div class="mt-auto d-flex justify-content-between align-items-center">
                                    <a href="dishes/restaurants/${restaurant.id}" class="btn btn-outline-primary">Подробнее</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </section>
    </#if>

    <section class="container mb-5">
        <div class="d-flex justify-content-between align-items-center mb-5">
            <h2>Популярные блюда</h2>
            <a href="/dishes" class="btn btn-outline-primary">Смотреть все</a>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="food-card card">
                    <div class="position-relative">
                        <img src="https://okeansushi.ru/templates/sushi/blog/pitstsa-pepperoni-istoriya-sostav-retsept-4.jpg" class="card-img-top" alt="Пицца Пепперони">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Пицца Пепперони</h5>
                        <p class="card-text text-muted">Острая колбаса, сыр моцарелла, томатный соус</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="price">850 C</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="food-card card">
                    <img src="https://sushiwok.ru/img/d8f116c4ad734759f875095afcfa30c6" class="card-img-top" alt="Ролл Филадельфия">
                    <div class="card-body">
                        <h5 class="card-title">Ролл Филадельфия</h5>
                        <p class="card-text text-muted">Лосось, сыр филадельфия, огурец, рис</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="price">890 С</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="food-card card">
                    <img src="https://www.chefmarket.ru/blog/wp-content/uploads/2020/11/hamburger-2000x1200.jpg" class="card-img-top" alt="Чизбургер">
                    <div class="card-body">
                        <h5 class="card-title">Чизбургер</h5>
                        <p class="card-text text-muted">Говяжья котлета, сыр, салат, соус</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="price">590 С</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="food-card card">
                    <div class="position-relative">
                        <img src="https://canapeclub.ru/image/cache/catalog/product/30-10-2019/cezar-818x818.jpg" class="card-img-top" alt="Салат Цезарь">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Салат Цезарь</h5>
                        <p class="card-text text-muted">Курица, салат айсберг, сухарики, соус цезарь</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="price">450 С</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="bg-light py-5 mb-5">
        <div class="container">
            <h2 class="text-center mb-5">Почему выбирают нас</h2>
            <div class="row text-center">
                <div class="col-md-4 mb-4">
                    <div class="p-4">
                        <i class="fas fa-bolt fa-3x mb-3 primary-color"></i>
                        <h4>Быстрая доставка</h4>
                        <p class="text-muted">Доставка в течение 45 минут или пицца бесплатно</p>
                    </div>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="p-4">
                        <i class="fas fa-utensils fa-3x mb-3 primary-color"></i>
                        <h4>Свежие продукты</h4>
                        <p class="text-muted">Только свежие и качественные ингредиенты</p>
                    </div>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="p-4">
                        <i class="fas fa-percent fa-3x mb-3 primary-color"></i>
                        <h4>Акции и скидки</h4>
                        <p class="text-muted">Регулярные акции и бонусная программа</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
</@layout.page>
