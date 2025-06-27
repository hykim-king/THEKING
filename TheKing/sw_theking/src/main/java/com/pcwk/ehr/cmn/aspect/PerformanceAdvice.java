/**
 * Package Name : com.pcwk.ehr.cmn.aspect <br/>
 * 파일명: PerformanceAdvice.java <br/>
 */
package com.pcwk.ehr.cmn.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

import com.pcwk.ehr.cmn.PLog;

/**
 * @author user
 *
 */

public class PerformanceAdvice implements PLog {
	public Object logExecutionTime(ProceedingJoinPoint pjp) throws Throwable {

		Object retObj = null;
		long start = System.currentTimeMillis();

		// 클래스 명
		String className = pjp.getTarget().getClass().getName();

		// 메서드명
		String methodName = pjp.getSignature().getName();

		//대상 메서드 실행
		retObj = pjp.proceed();

		long end = System.currentTimeMillis();

		long excutionTime = end - start;
		
		log.debug("│ className               │"+className);
		log.debug("│ methodName              │"+methodName);
		log.debug("^^^^^excutionTime:"+excutionTime+"ms");
		
		return retObj;
	}

}
