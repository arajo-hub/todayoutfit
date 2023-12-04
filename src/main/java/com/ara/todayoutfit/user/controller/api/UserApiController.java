package com.ara.todayoutfit.user.controller.api;

import com.ara.todayoutfit.common.BaseResult;
import com.ara.todayoutfit.common.ObjectResponse;
import com.ara.todayoutfit.common.ResultCode;
import com.ara.todayoutfit.user.request.UserCreateRequest;
import com.ara.todayoutfit.user.response.UserShow;
import com.ara.todayoutfit.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    /**
     * 아이디로 사용자 조회
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    public ObjectResponse<UserShow> getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }

    /**
     * 사용자 등록
     * @param request
     * @return
     */
    @PostMapping("/users")
    public BaseResult saveUser(@RequestBody @Valid UserCreateRequest request) {
        return userService.saveUser(request);
    }

    /**
     * 사용자 수정
     * @param id
     * @param request
     * @return
     */
    @PutMapping("/users/{id}")
    public BaseResult updateUser(@PathVariable String id, @RequestBody @Valid UserCreateRequest request) {
        return userService.updateUser(id, request);
    }

    /**
     * 사용자 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/users/{id}")
    public BaseResult deleteUser(@PathVariable String id) {
        userService.deleteById(id);
        return new BaseResult(ResultCode.SUCCESS);
    }

}
