<#import "../layout.ftlh" as layout>
<@layout.page title="Список блюд" styles="/static/css/main.css" activeNav="dishes">
    <div class="container mt-4">
        <h2 class="mb-4">
            <i class="fas fa-utensils me-2"></i>Список блюд
        </h2>

        <#if dishes?? && dishes?size gt 0>
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <#list dishes as dish>
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
                                        <i class="fas fa-store me-1"></i> ${dish.restaurant.name}
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
                                    <button class="btn btn-sm btn-outline-primary">
                                        <i class="fas fa-cart-plus me-1"></i> В корзину
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <div class="alert alert-info">
                <i class="fas fa-info-circle me-2"></i> Блюда не найдены
            </div>
        </#if>
    </div>
</@layout.page>