package com.kivi.framework.web.async;

import com.kivi.framework.constant.KtfError;
import com.kivi.framework.dto.KtfBaseRsp;
import com.kivi.framework.service.ITimeoutService;

public class KtfAsyncTimeoutRunnable<T> implements Runnable {

	private final KtfAsyncResult<T>	deferredResult;
	private final ITimeoutService	timeoutService;

	public KtfAsyncTimeoutRunnable(KtfAsyncResult<T> deferredResult, ITimeoutService timeoutService) {
		this.deferredResult	= deferredResult;
		this.timeoutService	= timeoutService;
	}

	@Override
	public void run() {

		KtfBaseRsp<String> rsp = new KtfBaseRsp<String>();
		rsp.setRspCode(KtfError.E_REQUEST_TIMEOUT);
		rsp.setRspDesc("请求超时");
		rsp.setRspBody("");

		if (timeoutService != null)
			timeoutService.onTimeout(deferredResult.getKtfMsgId());

		deferredResult.setErrorResult(rsp);
	}

}
