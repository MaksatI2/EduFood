<#import "../layout.ftlh" as layout>
<@layout.page title="Корзина" styles="/static/css/main.css" styles="/static/css/cart.css" activeNav="basket">
    <h1 class="my-4">Ваша корзина</h1>

    <#if successMessage??>
        <div class="alert alert-success">
            ${successMessage}
        </div>
    </#if>

    <#if errorMessage??>
        <div class="alert alert-danger">
            ${errorMessage}
        </div>
    </#if>

    <#if cartItems?size == 0>
        <div class="alert alert-info">Ваша корзина пуста.</div>
        <div class="mt-4">
            <a href="/restaurants" class="btn btn-primary">
                <i class="fas fa-utensils me-2"></i>Выбрать ресторан
            </a>
        </div>
    <#else>
        <div class="table-responsive">
            <table class="table align-middle table-hover">
                <thead class="table-light">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Блюдо</th>
                    <th scope="col">Ресторан</th>
                    <th scope="col">Количество</th>
                    <th scope="col">Цена</th>
                    <th scope="col">Итого</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list cartItems as item>
                    <tr>
                        <td>${(currentPage - 1) * 10 + item_index + 1}</td>
                        <td><strong>${item.dishName}</strong></td>
                        <td>${item.restaurantName}</td>
                        <td>
                            <form action="/orders/cart/update" method="post" class="quantity-control">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="dishId" value="${item.dishId}" />

                                <button type="submit" name="quantity" value="${item.quantity - 1}"
                                        class="quantity-btn" <#if item.quantity <= 1>disabled</#if>>
                                    -
                                </button>
                                <span class="quantity-input">${item.quantity}</span>
                                <button type="submit" name="quantity" value="${item.quantity + 1}"
                                        class="quantity-btn">
                                    +
                                </button>
                            </form>
                        </td>
                        <td>${item.dishPrice?string["#,##0.00"]} сом</td>
                        <td>${item.totalPrice?string["#,##0.00"]} сом</td>
                        <td>
                            <form action="/orders/cart/remove" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="dishId" value="${item.dishId}" />
                                <button class="btn btn-outline-danger btn-sm" type="submit">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </form>
                        </td>
                    </tr>
                </#list>
                </tbody>
                <tfoot>
                <tr class="table-secondary">
                    <td colspan="4" class="text-end fw-bold">Общая сумма:</td>
                    <td class="fw-bold">${totalPrice?string["#,##0.00"]} сом</td>
                    <td></td>
                </tr>
                </tfoot>
            </table>
        </div>

        <#if totalPages gt 1>
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item <#if currentPage == 1>disabled</#if>">
                        <a class="page-link" href="/orders/cart?page=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>

                    <#list 1..totalPages as pageNum>
                        <li class="page-item <#if pageNum == currentPage>active</#if>">
                            <a class="page-link" href="/orders/cart?page=${pageNum}">${pageNum}</a>
                        </li>
                    </#list>

                    <li class="page-item <#if currentPage == totalPages>disabled</#if>">
                        <a class="page-link" href="/orders/cart?page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </#if>

        <div class="d-flex justify-content-between align-items-center mt-4">
            <form action="/orders/cart/clear" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-danger" type="submit">
                    <i class="fas fa-trash-alt me-1"></i>Очистить корзину
                </button>
            </form>

            <form action="/orders/create" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-success btn-lg" type="submit">
                    <i class="fas fa-credit-card me-2"></i>Оформить заказ
                </button>
            </form>
        </div>
    </#if>
</@layout.page>