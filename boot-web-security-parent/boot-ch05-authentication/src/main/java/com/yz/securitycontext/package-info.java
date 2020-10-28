/**
 * Spring Security SecurityContext using in Sub Threads
 * {@link org.springframework.security.core.context.SecurityContextHolder}
 * delegate three mode
 * {@link org.springframework.security.core.context.SecurityContextHolderStrategy}
 * 1. local_thread_local current thread available
 * 2. inheritable_local_thread_local sub thread copy from parent thread
 * 3. global_thread_local all threads share
 */
package com.yz.securitycontext;