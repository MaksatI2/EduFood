<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as layout>

<@layout.page title="Профиль - EduFood" styles="/static/css/main.css" activeNav="profile">
    <style>
        .profile-card {
            background-color: white;
            border-radius: 15px;
            padding: 40px 30px;
            box-shadow: 0 6px 30px rgba(0, 0, 0, 0.06);
            margin-top: 50px;
            transition: all 0.3s ease-in-out;
        }

        .avatar {
            width: 130px;
            height: 130px;
            object-fit: cover;
            border-radius: 50%;
            border: 5px solid #ffa502;
        }

        .username {
            font-size: 1.8rem;
            font-weight: 600;
            color: #2f3542;
        }

        .email {
            font-size: 1rem;
            color: #57606f;
        }

        .section-title {
            font-weight: bold;
            font-size: 1.3rem;
            color: #2f3542;
            margin-top: 30px;
            margin-bottom: 15px;
        }

        .btn-orders {
            background-color: #ffa502;
            border: none;
            padding: 10px 20px;
            font-weight: 500;
            color: #fff;
            border-radius: 8px;
            transition: background-color 0.2s;
            text-decoration: none;
        }

        .btn-orders:hover {
            background-color: #e69500;
            text-decoration: none;
            color: #fff;
        }

        .alert {
            margin-top: 20px;
        }
    </style>

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
