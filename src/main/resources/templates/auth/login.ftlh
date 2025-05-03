<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>

<@main.page title="Вход - EduFood" styles="/static/css/main.css" styles="/static/css/auth.css" activeNav="login">
    <section class="login-section">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-5 col-md-8">
                    <div class="auth-card bg-white">
                        <h2 class="text-center mb-4">Вход в систему</h2>
                        <p class="text-center text-muted mb-4">Войдите, чтобы продолжить заказ</p>

                        <#if loginError??>
                            <div class="alert-danger">
                                ${loginError}
                            </div>
                        </#if>

                        <#if registrationSuccess??>
                            <div class="alert-success">
                                ${registrationSuccess}
                            </div>
                        </#if>

                        <#if authError??>
                            <div class="alert alert-danger">
                                ${authError}
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
                                    <input type="email" class="form-control" id="email" name="username" placeholder="Введите email">
                                </div>
                            </div>

                            <div class="mb-4">
                                <label for="password" class="form-label">Пароль</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-light"><i class="fas fa-lock"></i></span>
                                    <input type="password" class="form-control" id="password" name="password" placeholder="Введите пароль">
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