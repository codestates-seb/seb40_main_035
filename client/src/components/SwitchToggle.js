/* eslint-disable no-unused-vars */
import styled from 'styled-components';

const ToggleContainer = styled.div`
  justify-content: center;
  align-items: center;
  display: flex;
  z-index: 0;
`;

const CheckBox = styled.input`
  /* Not Checked => props로 받은 content(Text) (토글 왼쪽 위치) */
  ::before {
  }

  /* Not Checked => 빈 content를 block 형태로 생성 (토글 오른쪽 위치)*/
  ::after {
  }

  /* Checked => props로 받은 content(Text) (토글 오른쪽 위치) */
  ::before {
  }
  /* Checked => 빈 content를 block 형태로 생성 (토글의 왼쪽 위치) */
  ::after {
  }
`;

// 부모 컴포넌트로부터 props로 onChange 시 변경 값 전달할 함수,
const SwitchToggle = () => {
  return (
    <ToggleContainer>
      <CheckBox></CheckBox>
    </ToggleContainer>
  );
};

export default SwitchToggle;
