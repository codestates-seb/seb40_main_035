import styled from 'styled-components';

const ToggleContainer = styled.div`
  width: ${(props) => props.width ?? '100px'};
  align-items: center;
  justify-content: flex-start;
  display: flex;
  padding-left: 4px;
  background-color: ${(props) =>
    props.checked ? 'var(--purple)' : 'var(--purple-light)'};
  border: solid 2px var(--purple);
  border-radius: 20px;
`;

const CheckBoxContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;
const CheckBox = styled.input`
  cursor: pointer;
  /* z-index: 1; */
  width: 0rem;
  height: 2rem;
  border: solid 2px var(--purple-medium);
  border-radius: 20px;

  /* Not Checked => props로 받은 content(Text) (토글 왼쪽 위치) */
  ::before {
    position: absolute;
    content: '${(props) => props.left ?? '모집 중'}';
    width: 5rem;
    height: 2rem;
    display: flex;
    padding: 0 0 0 0.9em;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    color: var(--purple);
    font-weight: 700;
    font-size: 13px;
    /* 텍스트 트랜지션 */
    transition: all 0.2s ease-in-out;
  }

  /* Not Checked => 빈 content를 block 형태로 생성한 원 (토글 오른쪽 위치)*/
  ::after {
    position: relative;
    content: '';
    display: block;
    width: 1.4em;
    height: 1.4em;
    top: calc((2rem - 1.3em) / 2);
    left: calc(5.4rem - 2em);
    border-radius: 50%;
    background: var(--purple);
    /* 원 이동 트랜지션 */
    transition: all 0.2s ease-in-out;
  }
  &:checked {
    /* 배경색 변경 트랜지션 */
    transition: all 0.2s ease-in-out;

    /* Checked => props로 받은 content(Text) (토글 오른쪽 위치) */
    ::before {
      position: absolute;
      padding: 0 0 0 1.2em;
      content: '${(props) => props.right ?? '모집 완료'}';
      align-items: center;
      justify-content: center;
      font-weight: 700;
      font-size: 13px;
      color: white;
    }

    /* Checked => 빈 content를 block 형태로 생성한 원 (토글 왼쪽 위치) */
    ::after {
      content: '';
      z-index: 1;
      top: calc((2rem - 1.3em) / 2);
      left: calc((1.8rem - 1.3em) / 2);
      width: 1.4em;
      height: 1.4em;
      display: block;
      position: relative;
      background-color: white;
      border-radius: 50%;
    }
  }
`;

const SwitchToggle = ({ right, setChecked, onClick }) => {
  return (
    <ToggleContainer checked={!setChecked} width="100px">
      <CheckBoxContainer>
        <CheckBox
          left="모집 중"
          right={right}
          value=""
          type="checkbox"
          onClick={onClick}
        ></CheckBox>
      </CheckBoxContainer>
    </ToggleContainer>
  );
};

export default SwitchToggle;

// 부모 컴포넌트 예시입니다. 추후 삭제 할 예정입니다.
// export const ParentComponents = () => {
//   const [isCheck, setIsCheck] = useState(true);
//   return (
//     <div>
//       <SwitchToggle
//         right="모집 완료"
//         setChecked={isCheck}
//         onClick={() => {
//           setIsCheck(!isCheck);
//         }}
//         width="100px"
//       />
//     </div>
//   );
// };
