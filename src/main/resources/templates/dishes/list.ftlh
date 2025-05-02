<#import "../layout.ftlh" as layout>
<@layout.page title="Список блюд" styles="/static/css/main.css" activeNav="dishes">
    <style>
        /* Стили для модального окна (работают без JS) */
        .modal-checkbox {
            display: none;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.4);
        }

        .modal-checkbox:checked + .modal {
            display: block;
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 300px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .quantity-selector {
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 20px 0;
        }

        .quantity-btn {
            width: 40px;
            height: 40px;
            font-size: 20px;
            border: 1px solid #ddd;
            background-color: #f8f9fa;
            cursor: pointer;
            border-radius: 4px;
        }

        .quantity-input {
            width: 60px;
            height: 40px;
            text-align: center;
            margin: 0 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .modal-footer {
            display: flex;
            justify-content: space-between;
        }

        .modal-btn {
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
        }

        .confirm-btn {
            background-color: #0d6efd;
            color: white;
            border: none;
        }

        .cancel-btn {
            background-color: #f8f9fa;
            border: 1px solid #ddd;
        }

        input[type=number]::-webkit-inner-spin-button,
        input[type=number]::-webkit-outer-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }
    </style>

    <div class="container mt-4">
        <h2 class="mb-4">
            <i class="fas fa-utensils me-2"></i>Список блюд
        </h2>
        <style>
            :root {
                --pagination-active-bg: #ff6b6b;
                --pagination-active-border: #ff6b6b;
                --pagination-color: #ff6b6b;
                --pagination-hover-color: #d63031;
            }
            .pagination .page-item.active .page-link {
                background-color: var(--pagination-active-bg);
                border-color: var(--pagination-active-border);
                color: white;
            }

            .pagination .page-link {
                color: var(--pagination-color);
            }

            .pagination .page-link:hover {
                color: var(--pagination-hover-color);
            }
        </style>
        <#if error??>
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>
                <div>${error}</div>
            </div>
        </#if>

        <#if success??>
            <div class="alert alert-success d-flex align-items-center" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>
                <div>${success}</div>
            </div>
        </#if>

        <#if dishes?? && dishes?size gt 0>
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <#list dishes.content as dish>
                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <#if dish.photoUrl??>
                                <img src="${dish.photoUrl}" class="card-img-top" alt="${dish.name}" style="height: 200px; object-fit: cover;">
                            <#else>
                                <div class="bg-light d-flex align-items-center justify-content-center" style="height: 200px;">
                                    <i class="fas fa-utensils fa-4x text-secondary"></i>
                                </div>
                            </#if>
                            <div class="card-body">
                                <h5 class="card-title">${dish.name}</h5>
                                <p class="card-text text-muted">
                                    <#if dish.description??>
                                        ${dish.description}
                                    <#else>
                                        Описание отсутствует
                                    </#if>
                                </p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <span class="badge bg-primary">
                                        <i class="fas fa-store me-1"></i> ${dish.restaurantName}
                                    </span>
                                    <span class="fw-bold">${dish.price} С</span>
                                </div>
                            </div>
                            <div class="card-footer bg-white">
                                <div class="d-flex justify-content-between">
                                    <span class="text-muted">
                                        <i class="fas fa-weight-hanging me-1"></i>
                                        <#if dish.weight??>
                                            ${dish.weight} г
                                        <#else>
                                            —
                                        </#if>
                                    </span>
                                    <!-- Кнопка для открытия модального окна -->
                                    <label for="modal-${dish.id}" class="btn btn-sm btn-outline-primary">
                                        <i class="fas fa-cart-plus me-1"></i> В корзину
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Модальное окно для этого блюда -->
                    <input type="checkbox" id="modal-${dish.id}" class="modal-checkbox">
                    <div class="modal">
                        <div class="modal-content">
                            <h5>Выберите количество</h5>
                            <form method="POST" action="/orders/cart/add">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="dishId" value="${dish.id}">
                                <div class="quantity-selector">
                                    <!-- Кнопка "-" -->
                                    <button type="button" class="quantity-btn"
                                            onclick="this.nextElementSibling.stepDown(); this.nextElementSibling.onchange();">
                                        -
                                    </button>
                                    <!-- Поле ввода количества -->
                                    <input type="number" name="quantity" class="quantity-input" value="1" min="1"
                                           onchange="this.value = Math.max(1, this.value)">
                                    <!-- Кнопка "+" -->
                                    <button type="button" class="quantity-btn"
                                            onclick="this.previousElementSibling.stepUp(); this.previousElementSibling.onchange();">
                                        +
                                    </button>
                                </div>
                                <div class="modal-footer">
                                    <label for="modal-${dish.id}" class="modal-btn cancel-btn">Отмена</label>
                                    <button type="submit" class="modal-btn confirm-btn">Добавить</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </#list>
            </div>
        <nav class="mt-4" aria-label="Навигация по страницам">
            <ul class="pagination justify-content-center">
                <li class="page-item <#if dishes.first>disabled</#if>">
                    <a class="page-link" href="?page=0&size=${dishes.size}" aria-label="Первая">
                        <span aria-hidden="true">&laquo;&laquo;</span>
                    </a>
                </li>
                <li class="page-item <#if dishes.first>disabled</#if>">
                    <a class="page-link" href="?page=${dishes.number-1}&size=${dishes.size}" aria-label="Предыдущая">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <#list 1..dishes.totalPages as p>
                    <li class="page-item <#if (dishes.number+1) == p>active</#if>">
                        <a class="page-link" href="?page=${p-1}&size=${dishes.size}">${p}</a>
                    </li>
                </#list>
                <li class="page-item <#if dishes.last>disabled</#if>">
                    <a class="page-link" href="?page=${dishes.number+1}&size=${dishes.size}" aria-label="Следующая">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                <li class="page-item <#if dishes.last>disabled</#if>">
                    <a class="page-link" href="?page=${dishes.totalPages-1}&size=${dishes.size}" aria-label="Последняя">
                        <span aria-hidden="true">&raquo;&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
            <div class="text-center text-muted mt-2">
                Показано ${dishes.number*dishes.size+1}-${dishes.number*dishes.size + dishes.numberOfElements} из ${dishes.totalElements} блюд
            </div>
        <#else>
            <div class="alert alert-info">
                <i class="fas fa-info-circle me-2"></i> Блюда не найдены
            </div>
        </#if>
    </div>
</@layout.page>