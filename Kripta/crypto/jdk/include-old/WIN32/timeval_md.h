/*
 * @(#)timeval_md.h	1.12 00/02/02
 *
 * Copyright 1994-2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

#ifndef _JAVASOFT_WIN32_TIMEVAL_H_
#define _JAVASOFT_WIN32_TIMEVAL_H_

typedef struct {
	long tv_sec;
	long tv_usec;
} timeval_t;

/*
 * Operations on timevals.
 *
 * NB: timercmp does not work for >=, <= or ==.
 */
#define timerisset(tvp)         ((tvp)->tv_sec || (tvp)->tv_usec)
#define timercmp(tvp, uvp, cmp) \
        ((tvp)->tv_sec cmp (uvp)->tv_sec || \
         (tvp)->tv_sec == (uvp)->tv_sec && (tvp)->tv_usec cmp (uvp)->tv_usec)
#define timereq(tvp, uvp) \
         ((tvp)->tv_sec == (uvp)->tv_sec && (tvp)->tv_usec == (uvp)->tv_usec)
#define timerclear(tvp)         (tvp)->tv_sec = (tvp)->tv_usec = 0

void timeradd(timeval_t*, timeval_t*);
void timersub(timeval_t*, timeval_t*);

#endif /* !_JAVASOFT_WIN32_TIMEVAL_H_ */
