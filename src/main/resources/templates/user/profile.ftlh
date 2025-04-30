<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as layout>

<@layout.page title="Профиль - EduFood" styles="/static/css/main.css" activeNav="profile">
    <style>
        .profile-card {
            background-color: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
            margin-top: 50px;
        }

        .avatar {
            width: 120px;
            height: 120px;
            object-fit: cover;
            border-radius: 50%;
            border: 4px solid #ffa502;
        }

        .section-title {
            font-weight: bold;
            font-size: 1.3rem;
            color: #2f3542;
            margin-bottom: 20px;
        }
    </style>

    <section class="row justify-content-center">
        <div class="col-md-8">
            <div class="profile-card text-center">
                <img src="/static/avatar/default_avatar.jpg" alt="Avatar" class="avatar mb-3">
                <h3 class="mb-1">${user.username}</h3>
                <p class="text-muted">${user.email}</p>

                <hr class="my-4">

                <div class="text-start">
                    <div class="section-title">История заказов</div>
                    <p class="text-muted">История заказов пока недоступна.</p>
                </div>
            </div>
        </div>
    </section>
</@layout.page>
