package net.mureng.mureng.core.jwt.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.dto.ApiResult;
import net.mureng.mureng.core.exception.ResourceNotFoundException;
import net.mureng.mureng.core.jwt.component.JwtCreator;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "JWT 관련 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
public class JwtController {
    private final JwtCreator jwtCreator;
    private final MemberRepository memberRepository;

    @ApiOperation(value = "JWT 발급", notes = "JWT를 발급합니다.")
    @PostMapping()
    public ResponseEntity<ApiResult<String>> createJwt(
            @ApiParam(value = "사용자 Email", required = true) @RequestBody String email
         ) {

        Member member = memberRepository.findByEmail(email).orElseThrow(ResourceNotFoundException::new);

        String jwt = jwtCreator.createToken(member.getEmail(), member.getNickname());

        return ResponseEntity.ok(ApiResult.ok(jwt));
    }
}
