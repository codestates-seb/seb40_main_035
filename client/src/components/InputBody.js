import styled from 'styled-components';
import { useRecoilState } from 'recoil';
import { inputBodyState, inputHashTagsState } from '../atom/atom';
import { RiDeleteBack2Fill } from 'react-icons/ri';

const Container = styled.div`
  width: 100%;
  height: 100%;

  .title {
    border-bottom: 1px solid #b8b8b8; // 구분선
    font-size: 15px;
    font-weight: 500;

    padding: 10px 0;
    margin-bottom: 15px;
  }

  .content {
    .hashtag-list {
      /* height: 54px; */
      display: flex;
      flex-wrap: wrap;
    }

    .hashtag {
      border: 1px solid var(--purple);
      border-radius: 25px;
      background-color: var(--purple);
      color: white;
      cursor: pointer;

      white-space: nowrap;
      display: flex;
      align-items: center;
      margin: 0 15px 15px 0;
      padding: 5px 10px;
    }

    .delete {
      margin: 2px 0 0 5px;
      display: flex;
      align-items: center;
    }
  }
`;

const InputTextarea = styled.textarea`
  width: 100%;
  height: 50vh;
  padding: 8px 8px 10px;
  border-radius: 8px;
  border: none;
  color: var(--grey-dark);
  outline: none;
  border: 1px solid var(--purple-medium);
  transition: 300ms ease-in-out;
  white-space: nowrap;
  font-size: 16px;

  &:hover,
  &:focus,
  &:active {
    border-color: var(--purple);
  }

  &:focus,
  &:active {
    box-shadow: 0px 0px 0px 4px var(--purple-medium);
  }
`;

const InputHashTag = styled.input`
  width: 100%;
  height: 100%;
  padding: 8px 8px 10px;
  border-radius: 8px;
  border: none;
  color: var(--grey-dark);
  outline: none;
  border: 1px solid var(--purple-medium);
  transition: 300ms ease-in-out;
  white-space: nowrap;
  font-size: 16px;

  &:hover,
  &:focus,
  &:active {
    border-color: var(--purple);
  }

  &:focus,
  &:active {
    box-shadow: 0px 0px 0px 4px var(--purple-medium);
  }
`;

function InputBody() {
  const [inputBody, setInputBody] = useRecoilState(inputBodyState);
  const [inputHashTags, setInputHashTags] = useRecoilState(inputHashTagsState);

  const onInputKeyUp = (e) => {
    const filterTarget = inputHashTags.filter(
      (el) => el.hashtagName === e.target.value,
    );
    // 중복 확인 위한 arr(중복X:null, 중복:값O)

    if (
      e.key === 'Enter' &&
      filterTarget.length === 0 &&
      e.target.value.length > 0
    ) {
      let hashTagObj = {};
      hashTagObj.hashtagName = e.target.value;
      setInputHashTags([...inputHashTags, hashTagObj]); // 해시태그가 추가된 arr를 해시태그리스트 값으로 재설정
      e.target.value = '';
    } else if (
      filterTarget.length !== 0 &&
      e.key === 'Enter' // 중복 태그 시
    ) {
      e.target.value = '';
    }
  };

  const onDeleteClick = (index) => {
    const filtered = inputHashTags.filter((el, idx) => idx !== index);
    setInputHashTags(filtered);
  };

  return (
    <Container>
      <div className="title">프로젝트 계획을 설명해 주세요!</div>
      <InputTextarea
        placeholder={'계획'}
        value={inputBody}
        onChange={(e) => setInputBody(e.target.value)}
      />
      <div className="title">
        해시태그를 사용해 프로젝트에 대해 나타내보세요.
      </div>
      <div className="content">
        <ul className="hashtag-list">
          {
            // 선택, 입력된 태그들 표시
            inputHashTags.map((tag, idx) => (
              <li key={idx} className="hashtag">
                <div>#{tag.hashtagName}</div>
                <div
                  className="delete"
                  onClick={() => onDeleteClick(idx)}
                  aria-hidden="true"
                >
                  <RiDeleteBack2Fill />
                </div>
              </li>
            ))
          }
        </ul>

        <InputHashTag
          className="input-hashtag"
          placeholder="기술 태그를 직접 입력해주세요."
          onKeyUp={onInputKeyUp}
        />
      </div>
    </Container>
  );
}

export default InputBody;
