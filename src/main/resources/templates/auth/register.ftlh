<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>

<@main.page title="Регистрация - EduFood" styles="/static/css/main.css" styles="/static/css/auth.css" activeNav="register">
    <section class="registration-section">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-6 col-md-8">
                    <div class="auth-card bg-white">
                        <h2 class="text-center mb-4">Регистрация</h2>
                        <p class="text-center text-muted mb-4">Создайте аккаунт, чтобы заказывать любимую еду!</p>

                        <#if error??>
                            <div class="alert alert-danger d-flex align-items-center" role="alert">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                <div>${error}</div>
                            </div>
                        </#if>

                        <#if success??>
                            <div class="alert alert-success d-flex align-items-center" role="alert">
                                <i class="fas fa-check-circle me-2"></i>
                                <div>Регистрация прошла успешно!</div>
                            </div>
                        </#if>

                        <form action="/auth/register" method="post">
                            <#if _csrf??>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </#if>

                            <div class="mb-3">
                                <label for="username" class="form-label">Имя пользователя</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-light"><i class="fas fa-user"></i></span>
                                    <@spring.bind "user.username" />
                                    <input type="text" name="username" class="form-control <#if spring.status.error??>is-invalid</#if>" id="username" value="${spring.status.value!}" placeholder="Введите имя пользователя">
                                </div>
                                <#if spring.status.error??>
                                    <div class="invalid-feedback d-block mt-1">${spring.status.errorMessage}</div>
                                </#if>
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-light"><i class="fas fa-envelope"></i></span>
                                    <@spring.bind "user.email" />
                                    <input type="email" name="email" class="form-control <#if spring.status.error??>is-invalid</#if>" id="email" value="${spring.status.value!}" placeholder="Введите email">
                                </div>
                                <#if spring.status.error??>
                                    <div class="invalid-feedback d-block mt-1">${spring.status.errorMessage}</div>
                                </#if>
                            </div>

                            <div class="mb-4">
                                <label for="password" class="form-label">Пароль (минимум 6 символов)</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-light"><i class="fas fa-lock"></i></span>
                                    <@spring.bind "user.password" />
                                    <input type="password" name="password" class="form-control <#if spring.status.error??>is-invalid</#if>" id="password" placeholder="Введите пароль">
                                </div>
                                <#if spring.status.error??>
                                    <div class="invalid-feedback d-block mt-1">${spring.status.errorMessage}</div>
                                </#if>
                            </div>

                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary btn-lg">Зарегистрироваться</button>
                            </div>

                            <div class="text-center mt-4">
                                <p>Уже есть аккаунт? <a href="/auth/login" class="text-decoration-none" style="color: var(--primary-color);">Войти</a></p>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</@main.page>