<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>

<@main.page title="Вход - EduFood" styles="/static/css/main.css" activeNav="login">
    <style>
        :root {
            --primary-color: #ff6b6b;
            --secondary-color: #ffa502;
            --dark-color: #2f3542;
            --light-color: #f1f2f6;
        }

        .auth-card {
            border: none;
            border-radius: 15px;
            overflow: hidden;
            transition: transform 0.3s;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            padding: 30px;
        }

        .form-control:focus {
            box-shadow: none;
            border-color: var(--primary-color);
        }

        .form-label {
            font-weight: 500;
        }

        .login-section {
            padding: 70px 0;
        }
    </style>

    <section class="login-section">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-5 col-md-8">
                    <div class="auth-card bg-white">
                        <h2 class="text-center mb-4">Вход в систему</h2>
                        <p class="text-center text-muted mb-4">Войдите, чтобы продолжить заказ</p>

                        <#if error??>
                            <div class="alert alert-danger d-flex align-items-center" role="alert">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                <div>${error}</div>
                            </div>
                        </#if>

                        <#if authError??>
                            <div class="alert alert-danger d-flex align-items-center" role="alert">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                <div>${authError}</div>
                            </div>
                        </#if>

                        <#if success??>
                            <div class="alert alert-success d-flex align-items-center" role="alert">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                <div>${success}</div>
                            </div>
                        </#if>

                        <form method="post" action="/login">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                       value="${(_csrf.token)!'csrf-token'}"/>
                            </#if>
                            <div class="mb-3">
                                <label for="email" class="form-label">Электронная почта</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-light"><i class="fas fa-envelope"></i></span>
                                    <input type="email" class="form-control" id="email" name="username" placeholder="Введите email" required>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label for="password" class="form-label">Пароль</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-light"><i class="fas fa-lock"></i></span>
                                    <input type="password" class="form-control" id="password" name="password" placeholder="Введите пароль" required>
                                </div>
                            </div>

                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary btn-lg">Войти</button>
                            </div>

                            <div class="text-center mt-4">
                                <p>Нет аккаунта? <a href="/auth/register" class="text-decoration-none" style="color: var(--primary-color);">Зарегистрироваться</a></p>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</@main.page>