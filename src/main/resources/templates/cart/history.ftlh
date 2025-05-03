<#import "../layout.ftlh" as layout>
<@layout.page title="История заказов" styles="/static/css/main.css" styles="/static/css/cart.css" activeNav="profile">
    <div class="container py-5">
        <h2 class="mb-4">История заказов</h2>

        <#if orders?? && orders?size gt 0>
            <div class="row">
                <#list orders as order>
                    <div class="col-md-6 mb-4">
                        <a href="#orderModal${order.id}" class="card order-card text-decoration-none text-dark p-3">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <div class="order-number">Заказ #${order.id}</div>
                                    <div class="order-date">
                                        <i class="far fa-calendar-alt me-1"></i> ${order.formattedCreatedAt}
                                    </div>
                                </div>
                                <div class="text-end">
                                    <div class="order-total">${order.totalPrice} С</div>
                                    <div class="badge bg-primary">${order.dishes?size} блюда</div>
                                </div>
                            </div>
                        </a>
                    </div>

                    <div id="orderModal${order.id}" class="modal-css">
                        <div class="modal-content-css">
                            <a href="#" class="close-btn">×</a>
                            <div class="modal-header border-bottom">
                                <h5 class="modal-title">Заказ #${order.id}</h5>
                                <div class="text-muted small">${order.formattedCreatedAt}</div>
                            </div>
                            <div class="modal-body">
                                <#list order.dishes as dish>
                                    <div class="dish-item">
                                        <div class="restaurant-info">
                                            <#if dish.restaurantPhotoUrl?? && dish.restaurantPhotoUrl != "">
                                                <img src="/static/restaurants/${dish.restaurantPhotoUrl}" class="restaurant-thumbnail" alt="${dish.restaurantName!''}">
                                            <#else>
                                                <div class="restaurant-thumbnail img-placeholder">
                                                    <i class="fas fa-store-alt"></i>
                                                </div>
                                            </#if>
                                            <span class="small">${dish.restaurantName!''}</span>
                                        </div>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div class="d-flex align-items-center">
                                                <#if dish.photoUrl?? && dish.photoUrl != "">
                                                    <img src="${dish.photoUrl}" class="dish-img" alt="${dish.name!''}">
                                                <#else>
                                                    <div class="dish-img img-placeholder">
                                                        <i class="fas fa-utensils"></i>
                                                    </div>
                                                </#if>
                                                <div>
                                                    <div class="fw-bold">${dish.name!''}</div>
                                                    <div class="small text-muted">${dish.quantity} × ${dish.price} С</div>
                                                </div>
                                            </div>
                                            <div class="fw-bold">${dish.price * dish.quantity} С</div>
                                        </div>
                                    </div>
                                </#list>
                            </div>
                            <div class="modal-footer border-top">
                                <div class="d-flex justify-content-between w-100">
                                    <h5 class="mb-0">Итого:</h5>
                                    <h4 class="mb-0 text-primary">${order.totalPrice} С</h4>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <div class="text-center py-5">
                <i class="fas fa-box-open fa-4x text-muted mb-4"></i>
                <h4>У вас пока нет заказов</h4>
                <p class="text-muted">Сделайте свой первый заказ и он появится здесь</p>
                <a href="/restaurants" class="btn btn-primary">Выбрать ресторан</a>
            </div>
        </#if>
    </div>
</@layout.page>