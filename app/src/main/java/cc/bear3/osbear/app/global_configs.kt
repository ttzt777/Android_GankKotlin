package cc.bear3.osbear.app

/**
 * Description:
 * Author: TT
 * Since: 2020-03-23
 */
internal const val APP_ID = "5Lou18rk3OY0K6dUz8Ad"
internal const val APP_SECRET = "FjJ017hzgY8M9RGnCcl8rXqtGaLPB9AV"
internal const val APP_REDIRECT_URL = "https://www.oschina.net/"

private const val BASE_URL = "https://www.oschina.net/"
internal const val BASE_API_URL = "${BASE_URL}action/openapi/"
internal const val AUTHORIZE_URL = "${BASE_URL}action/oauth2/authorize?client_id=$APP_ID&response_type=code&redirect_uri=$APP_REDIRECT_URL"

internal const val AUTO_REFRESH_TOKEN_LIMIT = 60 * 1000

internal const val SP_NAME_ACCOUNT = "account_info"