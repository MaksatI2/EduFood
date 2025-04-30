<#import "/spring.ftl" as spring>
<#import "layout.ftlh" as layout>

<@layout.page title="EduFood - Доставка вкусной еды" styles="/static/css/main.css" activeNav="home">
    <section class="hero-section text-center">
        <div class="container">
            <h1 class="display-4 fw-bold mb-4">Доставка вкусной еды в вашем городе</h1>
            <p class="lead mb-5">Более 100 ресторанов с быстрой доставкой до двери</p>
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control form-control-lg" placeholder="Введите названия ресторана...">
                        <button class="btn btn-primary btn-lg" type="button">Найти рестораны</button>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="container mb-5">
        <div class="d-flex justify-content-between align-items-center mb-5">
            <h2>Популярные блюда</h2>
            <a href="#" class="btn btn-outline-primary">Смотреть все</a>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="food-card card">
                    <div class="position-relative">
                        <img src="/static/images/foods/pepperoni.jpg" class="card-img-top" alt="Пицца Пепперони">
                        <span class="discount-badge">-20%</span>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Пицца Пепперони</h5>
                        <p class="card-text text-muted">Острая колбаса, сыр моцарелла, томатный соус</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="price">799 ₽</span>
                            <button class="btn btn-sm btn-primary">В корзину</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="food-card card">
                    <img src="/static/images/foods/philadelphia.jpg" class="card-img-top" alt="Ролл Филадельфия">
                    <div class="card-body">
                        <h5 class="card-title">Ролл Филадельфия</h5>
                        <p class="card-text text-muted">Лосось, сыр филадельфия, огурец, рис</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="price">450 ₽</span>
                            <button class="btn btn-sm btn-primary">В корзину</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="food-card card">
                    <img src="/static/images/foods/cheeseburger.jpg" class="card-img-top" alt="Чизбургер">
                    <div class="card-body">
                        <h5 class="card-title">Чизбургер</h5>
                        <p class="card-text text-muted">Говяжья котлета, сыр, салат, соус</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="price">320 ₽</span>
                            <button class="btn btn-sm btn-primary">В корзину</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="food-card card">
                    <div class="position-relative">
                        <img src="/static/images/foods/caesar.jpg" class="card-img-top" alt="Салат Цезарь">
                        <span class="discount-badge">-15%</span>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Салат Цезарь</h5>
                        <p class="card-text text-muted">Курица, салат айсберг, сухарики, соус цезарь</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="price">350 ₽</span>
                            <button class="btn btn-sm btn-primary">В корзину</button>
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