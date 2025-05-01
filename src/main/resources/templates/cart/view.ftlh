<#import "../layout.ftlh" as layout>
<@layout.page title="Корзина" styles="/static/css/main.css" activeNav="basket">
    <h1 class="my-4">Ваша корзина</h1>

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
                    <th scope="col">Количество</th>
                    <th scope="col">Цена</th>
                    <th scope="col">Итого</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list cartItems as item>
                    <tr>
                        <td>${item_index + 1}</td>
                        <td><strong>${item.dishName}</strong></td>
                        <td>
                            <form action="/orders/cart/update" method="post" class="d-flex">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="dishId" value="${item.dishId}" />
                                <input type="number" name="quantity" value="${item.quantity}" min="1" class="form-control me-2" style="width: 80px;" />
                                <button class="btn btn-outline-primary btn-sm" type="submit"><i class="fas fa-sync-alt"></i></button>
                            </form>
                        </td>
                        <td>${item.dishPrice?string["#,##0.00"]} сом</td>
                        <td>${item.totalPrice?string["#,##0.00"]} сом</td>
                        <td>
                            <form action="/orders/cart/remove" method="post" onsubmit="return confirm('Удалить товар из корзины?');">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="dishId" value="${item.dishId}" />
                                <button class="btn btn-outline-danger btn-sm" type="submit"><i class="fas fa-trash"></i></button>
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

        <div class="d-flex justify-content-between align-items-center mt-4">
            <form action="/orders/cart/clear" method="post" onsubmit="return confirm('Очистить всю корзину?');">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-danger" type="submit"><i class="fas fa-trash-alt me-1"></i>Очистить корзину</button>
            </form>

            <form action="/orders/create" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-success btn-lg" type="submit"><i class="fas fa-credit-card me-2"></i>Оформить заказ</button>
            </form>
        </div>
    </#if>
</@layout.page>