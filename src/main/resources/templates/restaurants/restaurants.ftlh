<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>

<@main.page title="Рестораны - EduFood" styles="/static/css/main.css" styles="/static/css/restaurants.css" activeNav="restaurants">
    <section class="restaurants-header text-center">
        <div class="container">
            <h1 class="display-4 fw-bold mb-4">Рестораны</h1>
            <p class="lead">Выберите ресторан и закажите любимую еду с доставкой</p>
        </div>
    </section>

    <section class="mb-5">
        <div class="row mb-4">
            <div class="col-md-6">
                <h2>Все рестораны <span class="badge bg-primary rounded-pill">${totalItems}</span></h2>
            </div>
            <div class="col-md-6">
                <div class="input-group">
                    <form method="get" action="restaurants" class="input-group mb-3">
                        <input type="text" class="form-control" name="search" placeholder="Поиск ресторанов..." value="${search!}">
                        <button class="btn btn-primary" type="submit">
                            <i class="fas fa-search"></i>
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <div class="row">
            <#if restaurants?has_content>
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
            <#else>
                <div class="alert alert-info text-center" role="alert">
                    Рестораны не найдены.
                </div>
            </#if>
        </div>

        <#if totalPages gt 1>
            <nav aria-label="Навигация по ресторанам">
                <ul class="pagination justify-content-center">
                    <li class="page-item <#if currentPage == 0>disabled</#if>">
                        <a class="page-link" href="/restaurants?page=${currentPage - 1}" aria-label="Предыдущая">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>

                    <#list 0..totalPages-1 as i>
                        <li class="page-item <#if currentPage == i>active</#if>">
                            <a class="page-link" href="/restaurants?page=${i}">${i + 1}</a>
                        </li>
                    </#list>

                    <li class="page-item <#if currentPage == totalPages - 1>disabled</#if>">
                        <a class="page-link" href="/restaurants?page=${currentPage + 1}" aria-label="Следующая">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </#if>
    </section>
</@main.page>