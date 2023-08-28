package com.kivi.framework.web.async;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.dto.KtfBaseRsp;
import com.kivi.framework.service.ITimeoutService;

public class KtfTimeoutRunnable<T> implements Runnable {

	private final KtfAsyncResult<T>	deferredResult;
	private final ITimeoutService	timeoutService;

	public KtfTimeoutRunnable(KtfAsyncResult<T> deferredResult, ITimeoutService timeoutService) {
		this.deferredResult	= deferredResult;
		this.timeoutService	= timeoutService;
	}

	@Override
	public void run() {
		KtfBaseRsp<String> rsp = KtfBaseRsp.error(KtfError.E_REQUEST_TIMEOUT, "请求超时");

		if (timeoutService != null)
			timeoutService.onTimeout(deferredResult.getKtfMsgId());

		deferredResult.setErrorResult(rsp);
	}

}
