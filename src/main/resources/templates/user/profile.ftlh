<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as layout>

<@layout.page title="Профиль - EduFood" styles="/static/css/main.css" styles="/static/css/profile.css" activeNav="profile">
    <#if success??>
        <div class="alert alert-success d-flex align-items-center" role="alert">
            <i class="fas fa-check-circle me-2"></i>
            <div>${success}</div>
        </div>
    </#if>

    <section class="row justify-content-center">
        <div class="col-md-8">
            <div class="profile-card text-center">
                <img src="/static/avatar/default_avatar.jpg" alt="Avatar" class="avatar mb-3">
                <div class="username mb-1">${user.username}</div>
                <div class="email mb-3">${user.email}</div>

                <a href="/orders/history" class="btn-orders mt-3">История заказов</a>

            </div>
        </div>
    </section>
</@layout.page>
